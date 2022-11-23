package lv.ez.weather.service.weather;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "weatherapi")
public class WeatherApiEnvironmentConfiguration {
    private String basePath;
    private String apiKey;

    public String getBasePath() {
        return basePath;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
