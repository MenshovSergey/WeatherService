package cloud.weather.server.dao.user;

import cloud.weather.server.model.User;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> listUsers();

    void activateToggleUser(long id);

    User getByCredentials(String username, String password);

}
