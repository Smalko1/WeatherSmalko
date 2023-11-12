package com.smalko.weather.weather.location.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SearchWeatherForCoordinates {
    private double lat;
    private double lon;
    private CurrentWeather current;

    @Data
    public static class CurrentWeather {
        private long sunrise;
        private long sunset;
        private double temp;
        @JsonProperty("feels_like")
        private double feelsLike;
        private int humidity;
        private double uvi;
        private int clouds;
        @JsonProperty("wind_speed")
        private double windSpeed;

        @JsonProperty("wind_deg")
        private int windDeg;
        @JsonProperty("wind_gust")
        private double windGust;
        private List<Weather> weather;
        private Snow snow;
        private Rain rain;
    }

    @Data
    public static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;
    }

    @Data
    public static class Rain {
        @JsonProperty("1h")
        private double oneHour;
    }

    @Data
    public static class Snow {
        @JsonProperty("1h")
        private double oneHour;
    }
}

