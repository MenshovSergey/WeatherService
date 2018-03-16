package cloud.weather.server.service.api;

import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public class ApiServiceImpl implements ApiService {

    @Override
    public Object getVersion() {
        return new Version();
    }

    @Data
    private class Version {
        private String version = "v1";
    }

}
