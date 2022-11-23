package lv.ez.weather.service;

import lv.ez.weather.dto.LocationDto;
import lv.ez.weather.repositori.LocationRepository;
import lv.ez.weather.repository.LocationCache;
import lv.ez.weather.service.geolocation.GeoLocationEnvironmentConfiguration;
import lv.ez.weather.service.geolocation.GeoLocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GeolocationServiceTest {

    @InjectMocks
    private GeoLocationService geoLocationService;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private LocationCache locationCache;
    @Mock
    private GeoLocationEnvironmentConfiguration geoLocationEnvironmentConfiguration;

    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        geoLocationService = new GeoLocationService(locationRepository, locationCache, geoLocationEnvironmentConfiguration, modelMapper);
    }

    @Test
    public void retrieveLocationFromCache_Test() throws Exception {
        String ipAddress = "192.36.41.53";
        when(locationCache.isCachedIpAddress(ipAddress)).thenReturn(true);
        geoLocationService.retrieveLocation(ipAddress);
        verify(locationCache, times(1)).getCityFromCache(ipAddress);
    }

    @Test
    public void retrieveSaveLocation_Test() throws Exception {
        geoLocationService.saveLocation(new LocationDto("127.0.0.2", "Riga", 123123d));
        verify(locationRepository, times(1)).save(any());
    }
}
