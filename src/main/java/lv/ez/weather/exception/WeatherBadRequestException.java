package lv.ez.weather.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WeatherBadRequestException extends RuntimeException {
    public WeatherBadRequestException(String message) {
        super(message);
    }
}
