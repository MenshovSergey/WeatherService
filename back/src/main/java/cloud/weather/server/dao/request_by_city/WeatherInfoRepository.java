package cloud.weather.server.dao.request_by_city;

import cloud.weather.server.model.info.impl.WeatherInfo;
import org.springframework.data.repository.CrudRepository;

public interface WeatherInfoRepository extends CrudRepository<WeatherInfo, Long>, WeatherInfoRepositoryCustom {
}
