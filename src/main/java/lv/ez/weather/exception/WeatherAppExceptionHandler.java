package lv.ez.weather.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class WeatherAppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WeatherBadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(WeatherBadRequestException exception, HttpServletRequest request) {
        HttpStatus errorStatusCode = HttpStatus.BAD_REQUEST;
        Map<String, Object> errorResponseBody = buildErrorMessageBody(exception, request, errorStatusCode);
        return new ResponseEntity<>(errorResponseBody, errorStatusCode);
    }

    @ExceptionHandler(WeatherAppProcessingException.class)
    public ResponseEntity<Object> handleProcessingException(WeatherAppProcessingException exception, HttpServletRequest request) {
        HttpStatus errorStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        Map<String, Object> errorResponseBody = buildErrorMessageBody(exception, request, errorStatusCode);
        return new ResponseEntity<>(errorResponseBody, errorStatusCode);
    }

    private static Map<String, Object> buildErrorMessageBody(RuntimeException exception, HttpServletRequest request, HttpStatus errorStatusCode) {
        Map<String, Object> errorResponseBody = new LinkedHashMap<>();
        errorResponseBody.put("timestamp", LocalDateTime.now());
        errorResponseBody.put("status", errorStatusCode);
        errorResponseBody.put("error_message", exception.getMessage());
        errorResponseBody.put("path", request.getRequestURI());
        return errorResponseBody;
    }
}
