package cloud.weather.server.dao.user;

import cloud.weather.server.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {
    private static final Logger LOG = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        return (List<User>) entityManager.createQuery("from User").getResultList();
    }

    @Override
    public void activateToggleUser(long id) {
        User user = entityManager.find(User.class, id);
        boolean currentState = user.isActive();
        user.setActive(!currentState);
        entityManager.merge(user);
        LOG.info("User active state successfully changed. Details: " + user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public User getByCredentials(String username, String password) {
        String query = "from User where username = '" + username + "' and password = '" + password + "'";
        List<User> userList = entityManager.createQuery(query).getResultList();
        if (!userList.isEmpty()) {
            return userList.get(0);
        }

        return null;
    }
}
