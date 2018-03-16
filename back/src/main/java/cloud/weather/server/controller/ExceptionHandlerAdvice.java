package cloud.weather.server.controller;

import cloud.weather.server.ex.InvalidLineException;
import cloud.weather.server.ex.UserNotFoundException;
import cloud.weather.server.ex.UsernameAlreadyTakenExeption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.CredentialException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(CredentialException.class)
    public ResponseEntity handleCredentialsException(CredentialException e) {
        LOG.info("Wrong credits for username: " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Wrong credits");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserException(UserNotFoundException e) {
        LOG.info("User not found. Username: " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Find user error");
    }

    @ExceptionHandler(InvalidLineException.class)
    public ResponseEntity handleInvalidLineException(InvalidLineException e) {
        LOG.info("Invalid characters or empty line. Line: " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Invalid characters or empty line");
    }

    @ExceptionHandler(UsernameAlreadyTakenExeption.class)
    public ResponseEntity handleInvalidLineException(UsernameAlreadyTakenExeption e) {
        LOG.info("Username already taken. Username: " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Username '" + e.getMessage() + "' already taken.");
    }

}