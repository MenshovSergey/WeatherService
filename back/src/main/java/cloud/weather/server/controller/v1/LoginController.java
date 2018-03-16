package cloud.weather.server.controller.v1;

import cloud.weather.server.ex.InvalidLineException;
import cloud.weather.server.ex.UsernameAlreadyTakenExeption;
import cloud.weather.server.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.CredentialException;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(value = "/v1")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getLoginToken(HttpServletRequest request) throws CredentialException, InvalidLineException {
        return new ResponseEntity<>(loginService.login(request), HttpStatus.OK);
    }

    @GetMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getRegisterToken(HttpServletRequest request) throws InvalidLineException, UsernameAlreadyTakenExeption {
        return new ResponseEntity<>(loginService.register(request), HttpStatus.OK);
    }

}
