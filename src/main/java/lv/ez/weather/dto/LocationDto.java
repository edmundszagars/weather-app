package lv.ez.weather.dto;

public class LocationDto {
    private String ipAddress;
    private String city;
    private Double time;

    public LocationDto(String ipAddress, String city, Double time) {
        this.ipAddress = ipAddress;
        this.city = city;
        this.time = time;
    }

    public LocationDto() {
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "LocationDto{" +
                "ipAddress='" + ipAddress + '\'' +
                ", city='" + city + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
