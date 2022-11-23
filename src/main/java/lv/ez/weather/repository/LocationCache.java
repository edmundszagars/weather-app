package lv.ez.weather.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class LocationCache {

    private final StringRedisTemplate stringRedisTemplate;
    private final ValueOperations<String, String> valueOperations;

    @Autowired
    public LocationCache(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.valueOperations = stringRedisTemplate.opsForValue();
    }

    public void saveIp(String ip, String city) {
        valueOperations.set(ip, city);
    }

    public String getCityFromCache(String ipAddress) {
        return valueOperations.get(ipAddress);
    }

    public boolean isCachedIpAddress(String ipAddress){
        Boolean isCachedIp = stringRedisTemplate.hasKey(ipAddress);
        return Boolean.TRUE.equals(isCachedIp);
    }
}
