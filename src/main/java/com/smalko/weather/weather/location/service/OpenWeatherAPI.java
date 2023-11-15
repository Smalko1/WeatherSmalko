package com.smalko.weather.weather.location.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smalko.weather.weather.location.HttpStatus;
import com.smalko.weather.weather.location.json.SearchCityList;
import com.smalko.weather.weather.location.json.SearchWeatherForCoordinates;
import com.smalko.weather.weather.location.result.api.SearchCityResult;
import com.smalko.weather.weather.location.result.SearchWeatherResult;
import com.smalko.weather.weather.util.PropertiesUtil;
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

import static com.smalko.weather.weather.location.result.api.SearchCityResult.*;
import static com.smalko.weather.weather.location.result.SearchWeatherResult.*;
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

    public static SearchWeatherResult requestWeatherByCoordinate(Double lat, Double lon) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(createURIRequestApiByCoordinates(lat, lon))
                .GET()
                .timeout(Duration.of(5, MINUTES))
                .build();
        log.info("Http Request code {}", httpRequest.hashCode());
        SearchWeatherResult searchWeatherResult;
        switch (httpRequest.hashCode()) {
            case HTTP_BAD_REQUEST -> searchWeatherResult = searchWeatherResult(HttpStatus.HTTP_BAD_REQUEST);
            case HTTP_UNAUTHORIZED -> searchWeatherResult = searchWeatherResult(HttpStatus.HTTP_UNAUTHORIZED);
            case HTTP_NOT_FOUND -> searchWeatherResult = searchWeatherResult(HttpStatus.HTTP_NOT_FOUND);
            case HTTP_MANY_REQUESTS -> searchWeatherResult = searchWeatherResult(HttpStatus.HTTP_MANY_REQUESTS);
            default -> {
                searchWeatherResult = searchWeatherResult(convertingJsonStringToJavaObject(httpRequest, SearchWeatherForCoordinates.class));
            }
        }
        return searchWeatherResult;

    }


    public static SearchCityResult requestWeatherByCity(String city) {
        var httpRequest = HttpRequest.newBuilder()
                .uri(createURIRequestSearchCity(city))
                .GET()
                .timeout(Duration.of(5, MINUTES))
                .build();
        log.info("Http Request code {}", httpRequest.hashCode());
        SearchCityResult searchCityResult;

        switch (httpRequest.hashCode()) {
            case HTTP_BAD_REQUEST -> searchCityResult = createSearchCityResult(HttpStatus.HTTP_BAD_REQUEST);
            case HTTP_UNAUTHORIZED -> searchCityResult = createSearchCityResult(HttpStatus.HTTP_UNAUTHORIZED);
            case HTTP_NOT_FOUND -> searchCityResult = createSearchCityResult(HttpStatus.HTTP_NOT_FOUND);
            case HTTP_MANY_REQUESTS -> searchCityResult = createSearchCityResult(HttpStatus.HTTP_MANY_REQUESTS);
            default -> searchCityResult = createSearchCityResult(
                    List.of(convertingJsonStringToJavaObject(httpRequest, SearchCityList[].class))
            );

        }
        return searchCityResult;
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
