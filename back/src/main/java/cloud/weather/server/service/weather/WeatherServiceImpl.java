package cloud.weather.server.service.weather;

import cloud.weather.server.dao.request_by_city.WeatherInfoRepository;
import cloud.weather.server.dao.user.UserRepository;
import cloud.weather.server.model.User;
import cloud.weather.server.model.info.InfoResponse;
import cloud.weather.server.model.info.impl.ErrorInfo;
import cloud.weather.server.model.info.impl.HistoryInfo;
import cloud.weather.server.model.info.impl.WeatherInfo;
import cloud.weather.server.service.session.SessionManager;
import cloud.weather.server.utils.CitiesStorage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service
public class WeatherServiceImpl implements WeatherService {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherServiceImpl.class);
    private final static ScheduledExecutorService schExService = Executors.newScheduledThreadPool(1);
    private final static LinkedBlockingQueue<FutureTask<InfoResponse>> queue = new LinkedBlockingQueue<>();
    private final static ExecutorService executor = Executors.newFixedThreadPool(1);

    private final static URI API_URI = URI.create("http://api.openweathermap.org/data/2.5/weather");
    private final static String API_KEY = "c35e703a7be9014e07a7b302bfbd5cee";

    private final static Pattern validatePattern = Pattern.compile("[^a-zA-Zа-яА-Я0-9\\s]");

    @Autowired
    private WeatherInfoRepository weatherInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private CitiesStorage citiesStorage;

    @Autowired
    private ObjectMapper mapper;

    public WeatherServiceImpl() {
        schExService.scheduleWithFixedDelay(this::pollQueue, 0, 1, TimeUnit.SECONDS);
    }

    private void pollQueue() {
        try {
            if (!queue.isEmpty()) {
                executor.execute(queue.poll());
            }
        } catch (Exception e) {
            LOG.error("Error while execute task", e);
        }
    }

    @Override
    public InfoResponse getByCity(String city, String token) {
        try {
            if (token == null) {
                return getErrorResponse("Null token");
            }
            String username = sessionManager.getUsername(token);
            if (username == null) {
                return getErrorResponse("Wrong token or session expired");
            }

            User user = userRepository.findByUsername(username);
            if (user == null) {
                return getErrorResponse("User not found");
            }

            if (!isCorrectString(city)) {
                return getErrorResponse("Unacceptable symbols in city name");
            }
            URI uri = UriComponentsBuilder.fromUri(API_URI)
                    .queryParam("q", city)
                    .queryParam("appid", API_KEY)
                    .build()
                    .toUri();
            Callable<InfoResponse> infoCallable = () -> {
                try {
                    return mapResponse(mapper.readTree(getUri(uri)), user);
                } catch (Exception e) {
                    LOG.info("Api error", e.getMessage());
                    return getErrorResponse("Server Error");
                }
            };
            FutureTask<InfoResponse> future = new FutureTask<>(infoCallable);
            queue.offer(future);
            return future.get();
        } catch (InterruptedException e) {
            LOG.error("Executor thread interrupted", e);
        } catch (ExecutionException e) {
            LOG.error("Error while execution", e);
        }
        return getErrorResponse("Server Error");
    }

    @Override
    public InfoResponse getHistory(String token) {
        String username = sessionManager.getUsername(token);
        if (token == null) {
            return getErrorResponse("Null token");
        }
        if (username == null) {
            return getErrorResponse("Wrong token or session expired");
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            return getErrorResponse("User not found");
        }

        List<WeatherInfo> history = weatherInfoRepository.getUserHistory(user.getId());
        return new InfoResponse.Builder()
                .setType("HISTORY")
                .setInfo(new HistoryInfo(history))
                .build();
    }

    @Override
    public List<String> getAvailableCities() {
        return citiesStorage.getCities();
    }

    private boolean isCorrectString(String s) {
        return !validatePattern.matcher(s).find();
    }

    private InfoResponse mapResponse(JsonNode node, User user) {
        int code = node.get("cod").asInt();
        if (HttpStatus.valueOf(code) == HttpStatus.OK) {
            return new InfoResponse.Builder()
                    .setType("OK")
                    .setInfo(createWeatherInfo(node, user))
                    .build();
        }
        return getErrorResponse(node.get("message").asText());
    }

    private InfoResponse getErrorResponse(String message) {
        return new InfoResponse.Builder()
                .setType("ERROR")
                .setInfo(new ErrorInfo(message))
                .build();
    }

    private WeatherInfo createWeatherInfo(JsonNode node, User user) {
        WeatherInfo wi = new WeatherInfo();
        wi.setUserId(user.getId());
        wi.setFinished(true);
        wi.setCity(node.get("name").asText());
        wi.setDate(new Date());
        JsonNode main = node.get("main");
        wi.setTemp((int) (main.get("temp").asDouble() - 273.15));
        wi.setPressure((int) (main.get("pressure").asDouble() / 1000));
        JsonNode weather = node.get("weather").get(0);
        wi.setDescription(weather.get("description").asText());
        JsonNode coord = node.get("coord");
        wi.setLon(coord.get("lon").asDouble());
        wi.setLat(coord.get("lat").asDouble());
        wi.setImageUrl(getImageUri(weather.get("icon").asText()));

        addToHistory(wi);

        return wi;
    }

    private String getImageUri(String name) {
        return "http://openweathermap.org/img/w/" + name + ".png";
    }

    private void addToHistory(WeatherInfo wi) {
        weatherInfoRepository.save(wi);
    }

    private String getUri(URI uri) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }


}
