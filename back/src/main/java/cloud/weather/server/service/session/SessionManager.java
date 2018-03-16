package cloud.weather.server.service.session;

public interface SessionManager {

    void saveSession(String token, String username);

    String getUsername(String token);

}
