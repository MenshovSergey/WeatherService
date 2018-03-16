package cloud.weather.server.dao.request_by_city;

import cloud.weather.server.model.info.impl.WeatherInfo;

import java.util.List;

public interface WeatherInfoRepositoryCustom {

    List<WeatherInfo> getUserHistory(long userId);

    List<WeatherInfo> list();

}
