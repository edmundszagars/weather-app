package lv.ez.weather.controller;

import lv.ez.weather.dto.LocationDto;
import lv.ez.weather.dto.weather.WeatherDto;
import lv.ez.weather.service.geolocation.GeoLocationService;
import lv.ez.weather.service.weather.WeatherApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final GeoLocationService geoLocationService;
    private final WeatherApiService weatherApiService;

    @Autowired
    public WeatherController(GeoLocationService geoLocationService, WeatherApiService weatherApiService) {
        this.geoLocationService = geoLocationService;
        this.weatherApiService = weatherApiService;
    }

    @GetMapping
    public ResponseEntity<WeatherDto> getWeather(@RequestHeader("IP") String ipAddress) {
        LocationDto locationDto = geoLocationService.retrieveLocation(ipAddress);
        return ResponseEntity.ok(weatherApiService.getWeatherDataForCity(locationDto.getCity()));
    }
}
