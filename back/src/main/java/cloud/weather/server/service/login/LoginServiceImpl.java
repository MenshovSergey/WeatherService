package cloud.weather.server.service.login;

import cloud.weather.server.dao.user.UserRepository;
import cloud.weather.server.ex.InvalidLineException;
import cloud.weather.server.ex.UsernameAlreadyTakenExeption;
import cloud.weather.server.model.User;
import cloud.weather.server.model.info.InfoResponse;
import cloud.weather.server.model.info.impl.TokenInfo;
import cloud.weather.server.service.session.SessionManager;
import cloud.weather.server.utils.ShaCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.regex.Pattern;

@Service
public class LoginServiceImpl implements LoginService {

    private final static Pattern validatePattern = Pattern.compile("[^a-zA-Zа-яА-Я0-9]");

    @Autowired
    private UserRepository repository;

    @Autowired
    private SessionManager sessionManager;

    @Override
    public InfoResponse login(HttpServletRequest request) throws CredentialException, InvalidLineException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (isNotValid(username) || isNotValid(password)) {
            throw new InvalidLineException();
        }

        User user = repository.getByCredentials(username, ShaCrypt.hash(password));
        if (user == null) {
            throw new CredentialException(username);
        }

        String token = request.getSession().getId();
        sessionManager.saveSession(token, username);

        return new InfoResponse.Builder()
                .setType("TOKEN")
                .setInfo(new TokenInfo(token))
                .build();
    }

    @Override
    public InfoResponse register(HttpServletRequest request) throws InvalidLineException, UsernameAlreadyTakenExeption {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (isNotValid(username) || isNotValid(password)) {
            throw new InvalidLineException();
        }

        User user = repository.findByUsername(username);
        if (user != null) {
            throw new UsernameAlreadyTakenExeption(username);
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(ShaCrypt.hash(password));
        newUser.setActive(true);
        newUser.setCreatedTimestamp(new Date());
        repository.save(newUser);

        String token = request.getSession().getId();
        sessionManager.saveSession(token, username);

        return new InfoResponse.Builder()
                .setType("TOKEN")
                .setInfo(new TokenInfo(token))
                .build();
    }

    private boolean isNotValid(String s) {
        return s == null || validatePattern.matcher(s).find();
    }

}
