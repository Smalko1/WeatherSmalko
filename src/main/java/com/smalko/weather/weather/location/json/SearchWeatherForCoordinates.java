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
        @JsonProperty("wind_gust")
        private Double windGust;
        @JsonProperty("wind_deg")
        private Integer windDeg;
        private List<Weather> weather;
        @JsonProperty("rain")
        private Rain rain;

        @JsonProperty("snow")
        private Snow snow;

        public String getWindDirection() {
            if (windDeg > 22.5 && windDeg <= 67.5) {
                return "North-East";
            } else if (windDeg > 67.5 && windDeg <= 97.5) {
                return "East";
            } else if (windDeg > 97.5 && windDeg <= 112.5) {
                return "South-East";
            } else if (windDeg > 112.5 && windDeg <= 157.5) {
                return "South";
            } else if (windDeg > 157.5 && windDeg <= 202.5) {
                return "South-West";
            } else if (windDeg > 202.5 && windDeg <= 247.5) {
                return "West";
            } else if (windDeg > 247.5 && windDeg <= 292.5) {
                return "North-West";
            } else if (windDeg > 292.5 && windDeg <= 337.5) {
                return "North";
            } else {
                return "North";
            }
        }
    }

    @Data
    public static class Weather {
        private Integer id;
        private String main;
        private String description;
        private String icon;
    }

    @Data
    public static class Rain {
        @JsonProperty("1h")
        private Double oneHour;
    }

    @Data
    public static class Snow {
        @JsonProperty("1h")
        private Double oneHour;
    }





}
