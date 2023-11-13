package com.smalko.weather.weather.location.result.api;

import com.smalko.weather.weather.location.HttpStatus;
import com.smalko.weather.weather.location.json.SearchWeatherForCoordinates;
import lombok.Getter;

@Getter
public class SearchWeatherResult {
    private final SearchWeatherForCoordinates weather;
    private final HttpStatus status;

    public static SearchWeatherResult searchWeatherResult(SearchWeatherForCoordinates weather){
        return new SearchWeatherResult(weather, HttpStatus.HTTP_OK);
    };

    public static SearchWeatherResult searchWeatherResult(HttpStatus status){
        return new SearchWeatherResult(null, status);
    };



    private SearchWeatherResult(SearchWeatherForCoordinates weather, HttpStatus status) {
        this.weather = weather;
        this.status = status;
    }
}
