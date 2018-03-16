package cloud.weather.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitiesStorage {

    private static final Logger LOG = LoggerFactory.getLogger(CitiesStorage.class);
    private static List<String> cities = new ArrayList<>();

    @Value("${cities.file}")
    private String resourceCityFilePath;

    public List<String> getCities() {
        if (cities.isEmpty()) {
            try {
                FileOperations fo = new FileOperations();
                cities = fo.readResourceFileToStringList(resourceCityFilePath, "UTF-8")
                        .stream()
                        .skip(1)
                        .map(str -> str.split("\t"))
                        .filter(el -> el.length == 5)
                        .map(el -> el[1] + "," + el[4].toLowerCase())
                        .collect(Collectors.toList());
            } catch (IOException e) {
                LOG.error("Can't read cities file", e);
            }
        }
        return cities;
    }

}
