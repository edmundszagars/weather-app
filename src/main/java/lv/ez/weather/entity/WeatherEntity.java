package lv.ez.weather.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WEATHER")
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String region;
    private String country;
    private Long time;
    @Column(name = "TEMP_C")
    private Double tempC;
    @Column(name = "TEMP_F")
    private Double tempF;
    @Column(name = "FEELS_LIKE_C")
    private Double feelsLikeC;
    @Column(name = "FEELS_LIKE_F")
    private Double feelsLikeF;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Double getTempC() {
        return tempC;
    }

    public void setTempC(Double tempC) {
        this.tempC = tempC;
    }

    public Double getTempF() {
        return tempF;
    }

    public void setTempF(Double tempF) {
        this.tempF = tempF;
    }

    public Double getFeelsLikeC() {
        return feelsLikeC;
    }

    public void setFeelsLikeC(Double feelsLikeC) {
        this.feelsLikeC = feelsLikeC;
    }

    public Double getFeelsLikeF() {
        return feelsLikeF;
    }

    public void setFeelsLikeF(Double feelsLikeF) {
        this.feelsLikeF = feelsLikeF;
    }
}
