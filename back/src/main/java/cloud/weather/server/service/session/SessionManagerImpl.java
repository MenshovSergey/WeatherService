package cloud.weather.server.service.session;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionManagerImpl implements SessionManager {

    private ConcurrentHashMap<String, String> sessions = new ConcurrentHashMap<>();

    @Override
    public void saveSession(String token, String username) {
        sessions.put(token, username);
    }

    public String getUsername(String token) {
        return sessions.get(token);
    }

}
