package com.smalko.weather.weather.location.result;

import com.smalko.weather.weather.location.json.SearchWeatherForCoordinates;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
@EqualsAndHashCode
public class SearchWeatherResult {
    private final List<SearchWeatherForCoordinates> weather;
    private final boolean successful;
    private final Exception exception;

    public static SearchWeatherResult result(List<SearchWeatherForCoordinates> weather){
        return new SearchWeatherResult(weather, true,  null);
    }

    public static SearchWeatherResult result(Exception error){
        return new SearchWeatherResult(Collections.emptyList(), false,  error);
    }

    private SearchWeatherResult(List<SearchWeatherForCoordinates> weather, boolean successful, Exception error) {
        this.weather = weather;
        this.successful = successful;
        this.exception = error;
    }
}
