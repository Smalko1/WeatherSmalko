package com.smalko.weather.weather.location.result;

import com.smalko.weather.weather.location.json.SearchWeatherForCoordinates;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode
public class SearchWeatherResult {
    private final SearchWeatherForCoordinates weather;
    private final boolean successful;
    private final Exception exception;

    public static SearchWeatherResult result(SearchWeatherForCoordinates weather){
        return new SearchWeatherResult(weather, true,  null);
    }

    public static SearchWeatherResult result(Exception error){
        return new SearchWeatherResult(null, false,  error);
    }

    private SearchWeatherResult(SearchWeatherForCoordinates weather, boolean successful, Exception error) {
        this.weather = weather;
        this.successful = successful;
        this.exception = error;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
