package com.smalko.weather.weather.location.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchWeatherForCoordinates {
    private String cityName;
    private Double lat;
    private Double lon;
    private String timezone;
    private WeatherInfo current;

    @Data
    public static class WeatherInfo {
        private Long dt;
        private Long sunrise;
        private Long sunset;
        private Double temp;
        @JsonProperty("feels_like")
        private Double feelsLike;
        private Integer pressure;
        private Integer humidity;
        @JsonProperty("dew_point")
        private Double dewPoint;
        private Integer uvi;
        private Integer clouds;
        private Integer visibility;
        @JsonProperty("wind_speed")
        private Double windSpeed;
        @JsonProperty("wind_deg")
        private Integer windDeg;
        private List<Weather> weather;
    }

    @Data
    public static class Weather {
        private Integer id;
        private String main;
        private String description;
        private String icon;
    }
}
