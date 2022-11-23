package lv.ez.weather.service.geolocation;

import io.ipgeolocation.api.Geolocation;
import io.ipgeolocation.api.GeolocationParams;
import io.ipgeolocation.api.IPGeolocationAPI;
import lv.ez.weather.dto.LocationDto;
import lv.ez.weather.entity.LocationEntity;
import lv.ez.weather.repositori.LocationRepository;
import lv.ez.weather.repository.LocationCache;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class GeoLocationService {
    private static final Logger logger = LoggerFactory.getLogger(GeoLocationService.class);
    private final LocationRepository locationRepository;
    private final GeoLocationEnvironmentConfiguration geoLocationEnvironmentConfiguration;
    private final LocationCache locationCache;
    private final ModelMapper modelMapper;
    private IPGeolocationAPI ipGeolocation;

    @Autowired
    public GeoLocationService(LocationRepository locationRepository, LocationCache locationCache,
                              GeoLocationEnvironmentConfiguration geoLocationEnvironmentConfiguration, ModelMapper modelMapper) {
        this.locationRepository = locationRepository;
        this.locationCache = locationCache;
        this.geoLocationEnvironmentConfiguration = geoLocationEnvironmentConfiguration;
        this.modelMapper = modelMapper;
    }

    public LocationDto retrieveLocation(String ipAddress) {
        if (locationCache.isCachedIpAddress(ipAddress)) {
            return saveLocationFromCache(ipAddress);
        } else {
            LocationDto locationDto = retrieveGeolocation(ipAddress);
            locationCache.saveIp(ipAddress, locationDto.getCity());
            saveLocation(locationDto);
            return locationDto;
        }
    }

    public LocationDto saveLocationFromCache(String ipAddress) {
        LocationDto locationDto = new LocationDto();
        locationDto.setIpAddress(ipAddress);
        locationDto.setCity(
                locationCache.getCityFromCache(ipAddress));
        locationDto.setTime((double) Instant.now().getEpochSecond());
        logger.info("Saving from cache!");
        saveLocation(locationDto);
        return locationDto;
    }

    private LocationDto retrieveGeolocation(String ipAddress) {
        ipGeolocation = new IPGeolocationAPI(geoLocationEnvironmentConfiguration.getKey());

        GeolocationParams geolocationParams = new GeolocationParams();
        geolocationParams.setIPAddress(ipAddress);
        geolocationParams.setFields("city,time_zone");

        Geolocation geolocation = ipGeolocation.getGeolocation(geolocationParams);
        LocationDto locationDto = new LocationDto();
        locationDto.setIpAddress(ipAddress);
        locationDto.setCity(geolocation.getCity());
        locationDto.setTime(geolocation.getTimezone().getCurrentTimeUnix());
        logger.info("Saving from Geolocator!");
        return locationDto;
    }

    public void saveLocation(LocationDto locationDto) {
        LocationEntity locationEntity = modelMapper.map(locationDto, LocationEntity.class);
        locationRepository.save(locationEntity);
    }
}
