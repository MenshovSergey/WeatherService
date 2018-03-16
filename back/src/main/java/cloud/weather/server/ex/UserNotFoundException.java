package cloud.weather.server.ex;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
        super(message);
    }

}
