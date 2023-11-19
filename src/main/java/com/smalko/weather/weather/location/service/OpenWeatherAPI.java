package com.smalko.weather.weather.location.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smalko.weather.weather.location.errors.ResponseExceptionWeather;
import com.smalko.weather.weather.location.errors.TooManyRequestsExceptionWeather;
import com.smalko.weather.weather.location.json.SearchCityList;
import com.smalko.weather.weather.location.json.SearchWeatherForCoordinates;
import com.smalko.weather.weather.util.PropertiesUtil;
import com.smalko.weather.weather.location.errors.NotFoundExceptionWeather;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import static java.net.HttpURLConnection.*;
import static java.time.temporal.ChronoUnit.*;

public class OpenWeatherAPI {
    private static final int HTTP_MANY_REQUESTS = 429;
    private static final Logger log = LoggerFactory.getLogger(OpenWeatherAPI.class);
    private static final String WEATHER_REQUEST_BY_COORDINATES =
            "https://api.openweathermap.org/data/3.0/onecall?lat=%s&lon=%s&units=metric&exclude=minutely,hourly,daily,alerts&appid=%s";
    private static final String SEARCH_COORDINATES_FOR_CITY =
            "http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=5&appid=%s";


    @SneakyThrows
    private static URI createURIRequestApiByCoordinates(Double lat, Double lon) {
        log.info("Create URI request");
        return new URI(WEATHER_REQUEST_BY_COORDINATES.formatted(
                lat.toString(),
                lon.toString(),
                PropertiesUtil.get("api_key")
        ));
    }

    @SneakyThrows
    private static URI createURIRequestSearchCity(String city) {
        log.info("Create URI request");
        return new URI(SEARCH_COORDINATES_FOR_CITY.formatted(
                city,
                PropertiesUtil.get("api_key")
        ));
    }

    public static SearchWeatherForCoordinates requestWeatherByCoordinate(Double lat, Double lon) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(createURIRequestApiByCoordinates(lat, lon))
                .GET()
                .timeout(Duration.of(5, MINUTES))
                .build();
        log.info("Http Request code {}", httpRequest.hashCode());
        switch (httpRequest.hashCode()) {
            case HTTP_BAD_REQUEST -> throw new ResponseExceptionWeather();
            case HTTP_NOT_FOUND -> throw new NotFoundExceptionWeather();
            case HTTP_MANY_REQUESTS -> throw new TooManyRequestsExceptionWeather();
            default -> {
                return convertingJsonStringToJavaObject(httpRequest, SearchWeatherForCoordinates.class);
            }
        }
    }


    public static List<SearchCityList> requestWeatherByCity(String city) {
        var httpRequest = HttpRequest.newBuilder()
                .uri(createURIRequestSearchCity(city))
                .GET()
                .timeout(Duration.of(5, MINUTES))
                .build();
        log.info("Http Request code {}", httpRequest.hashCode());

        switch (httpRequest.hashCode()) {
            case HTTP_BAD_REQUEST -> throw new ResponseExceptionWeather();
            case HTTP_MANY_REQUESTS -> throw new TooManyRequestsExceptionWeather();
            default -> {
                return List.of(convertingJsonStringToJavaObject(httpRequest, SearchCityList[].class));
            }

        }
    }

    private static <T> T convertingJsonStringToJavaObject(HttpRequest request, Class<T> clazz) {
        try {
            HttpResponse<String> send = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(send.body());
            var objectMapper = new ObjectMapper();
            return objectMapper.readValue(send.body(), clazz);
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


}
