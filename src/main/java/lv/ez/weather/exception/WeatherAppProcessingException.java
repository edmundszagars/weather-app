package lv.ez.weather.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class WeatherAppProcessingException extends RuntimeException {
    public WeatherAppProcessingException(String message) {
        super(message);
    }
}
