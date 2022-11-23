package lv.ez.weather.service;

import liquibase.pro.packaged.W;
import lv.ez.weather.dto.weather.Current;
import lv.ez.weather.dto.weather.Location;
import lv.ez.weather.dto.weather.WeatherResponseDto;
import lv.ez.weather.entity.WeatherEntity;
import lv.ez.weather.exception.WeatherAppProcessingException;
import lv.ez.weather.repositori.WeatherRepository;
import lv.ez.weather.service.weather.WeatherApiEnvironmentConfiguration;
import lv.ez.weather.service.weather.WeatherApiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherApiServiceTest {
    @InjectMocks
    private WeatherApiService weatherApiService;
    @Mock
    private WeatherApiEnvironmentConfiguration weatherApiEnvironmentConfiguration;
    @Mock
    private WeatherRepository weatherRepository;

    @Test
    public void buildValidUrl_Test() {
        String queryParam = "Riga";
        when(weatherApiEnvironmentConfiguration.getBasePath())
                .thenReturn("http://api.weatherapi.com/v1/current.json");
        when(weatherApiEnvironmentConfiguration.getApiKey())
                .thenReturn("3987f9873987s987s98d7");
        URI url = weatherApiService.buildUrl(queryParam);
        assertEquals("http://api.weatherapi.com/v1/current.json?key=3987f9873987s987s98d7&q=Riga", url.toString());
    }

    @Test
    public void saveWeatherData_Test() {
        WeatherResponseDto weatherResponseDto = new WeatherResponseDto();
        weatherResponseDto.setLocation(new Location());
        weatherResponseDto.setCurrent(new Current());
        weatherResponseDto.getLocation().setCountry("Latvia");
        weatherResponseDto.getLocation().setRegion("Riga");
        weatherResponseDto.getLocation().setLocaltime("320983049808");
        weatherResponseDto.getCurrent().setFeelslike_c(34.6);
        weatherResponseDto.getCurrent().setFeelslike_f(56.8);
        weatherResponseDto.getCurrent().setTemp_c(-1);
        weatherResponseDto.getCurrent().setTemp_f(25);

        weatherApiService.saveWeatherData(weatherResponseDto);
        verify(weatherRepository, times(1)).save(any());
    }

    @Test
    public void buildInvalidUrlException_Test() {
        String queryParam = "Riga";
        String expectedErrorMessage = "Failed to build URL for 'WeatherApi'";
        when(weatherApiEnvironmentConfiguration.getBasePath())
                .thenReturn("NOT_A_VALID_PATH");
        Exception exception = assertThrows(WeatherAppProcessingException.class, () -> {
            weatherApiService.buildUrl(queryParam);
        });
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
