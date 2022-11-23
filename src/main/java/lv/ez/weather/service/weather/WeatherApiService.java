package lv.ez.weather.service.weather;

import lv.ez.weather.dto.weather.Current;
import lv.ez.weather.dto.weather.Location;
import lv.ez.weather.dto.weather.WeatherDto;
import lv.ez.weather.dto.weather.WeatherResponseDto;
import lv.ez.weather.entity.WeatherEntity;
import lv.ez.weather.exception.WeatherAppProcessingException;
import lv.ez.weather.repositori.WeatherRepository;
import lv.ez.weather.service.geolocation.GeoLocationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class WeatherApiService {

    private static final Logger logger = LoggerFactory.getLogger(GeoLocationService.class);
    private final WeatherApiEnvironmentConfiguration weatherApiEnvironmentConfiguration;
    private final RestTemplate restTemplate;
    private final WeatherRepository weatherRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public WeatherApiService(WeatherApiEnvironmentConfiguration weatherApiEnvironmentConfiguration, RestTemplate restTemplate,
                             WeatherRepository weatherRepository, ModelMapper modelMapper) {
        this.weatherApiEnvironmentConfiguration = weatherApiEnvironmentConfiguration;
        this.restTemplate = restTemplate;
        this.weatherRepository = weatherRepository;
        this.modelMapper = modelMapper;
    }

    public WeatherDto getWeatherDataForCity(String city) {
        WeatherResponseDto weatherResponse = retrieveWeatherData(city);
        WeatherEntity weatherEntity = saveWeatherData(weatherResponse);
        return modelMapper.map(weatherEntity, WeatherDto.class);
    }

    public WeatherResponseDto retrieveWeatherData(String city) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            URI url = buildUrl(city);
            logger.info("Request weather update for: " + city);
            return restTemplate.exchange(url, HttpMethod.GET, entity, WeatherResponseDto.class)
                    .getBody();
        } catch (RestClientException rce) {
            final String errorMessage = "Failed to retrieve weather update!";
            logger.error(errorMessage, rce);
            throw new WeatherAppProcessingException(errorMessage);
        }
    }

    public WeatherEntity saveWeatherData(WeatherResponseDto weatherResponseDto) {
        WeatherEntity weatherData = new WeatherEntity();
        weatherData.setCountry(weatherResponseDto.getLocation().getCountry());
        weatherData.setRegion(weatherResponseDto.getLocation().getRegion());
        weatherData.setTime(weatherResponseDto.getLocation().getLocaltime_epoch());
        weatherData.setFeelsLikeC(weatherResponseDto.getCurrent().getFeelslike_c());
        weatherData.setFeelsLikeF(weatherResponseDto.getCurrent().getFeelslike_f());
        weatherData.setTempC(weatherResponseDto.getCurrent().getTemp_c());
        weatherData.setTempF(weatherResponseDto.getCurrent().getTemp_f());

        weatherRepository.save(weatherData);
        return weatherData;
    }

    public URI buildUrl(String cityQueryParamValue) {
        try {
            return UriComponentsBuilder
                    .fromHttpUrl(weatherApiEnvironmentConfiguration.getBasePath())
                    .queryParam("key", weatherApiEnvironmentConfiguration.getApiKey())
                    .queryParam("q", cityQueryParamValue)
                    .build()
                    .toUri()
                    .toURL().toURI();
        } catch (IllegalArgumentException | MalformedURLException | URISyntaxException e) {
            final String errorMessage = "Failed to build URL for 'WeatherApi'";
            logger.error(errorMessage, e);
            throw new WeatherAppProcessingException(errorMessage);
        }
    }
}
