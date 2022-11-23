package lv.ez.weather.controller;

import lv.ez.weather.dto.LocationDto;
import lv.ez.weather.service.geolocation.GeoLocationService;
import lv.ez.weather.service.weather.WeatherApiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

    private static final String WEATHER_PATH = "/weather";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GeoLocationService geoLocationService;
    @MockBean
    private WeatherApiService weatherApiService;

    @Test
    public void getWeather_Test() throws Exception {
        String ipAddress = "192.36.41.53";
        when(geoLocationService.retrieveLocation(ipAddress))
                .thenReturn(new LocationDto(ipAddress, "Riga", 1669203197d));
        mockMvc.perform(MockMvcRequestBuilders.get(WEATHER_PATH)
                        .header("IP", ipAddress))
                .andExpect(status().isOk());
    }

    @Test
    public void getWeatherInvalidHeader_Test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(WEATHER_PATH)
                        .header("IP", "invalid-ip-address"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("error_message").exists());
    }
}
