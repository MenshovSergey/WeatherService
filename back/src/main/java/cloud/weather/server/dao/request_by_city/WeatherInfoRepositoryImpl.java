package cloud.weather.server.dao.request_by_city;

import cloud.weather.server.model.info.impl.WeatherInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class WeatherInfoRepositoryImpl implements WeatherInfoRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<WeatherInfo> getUserHistory(long userId) {
        return (List<WeatherInfo>) entityManager
                .createQuery("from WeatherInfo where user_id ='" + userId + "'")
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<WeatherInfo> list() {
        return (List<WeatherInfo>) entityManager
                .createQuery("from WeatherInfo")
                .getResultList();
    }

}
