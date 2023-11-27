package com.smalko.weather.weather.location.result;

import com.smalko.weather.weather.location.json.SearchWeatherForCoordinates;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@EqualsAndHashCode
@Setter
public class SearchWeatherResult {
    private Integer locationId;
    private final SearchWeatherForCoordinates weather;
    private final boolean successful;
    private final Exception exception;

    public static SearchWeatherResult result(Integer locationId, SearchWeatherForCoordinates weather) {
        return new SearchWeatherResult(locationId, weather, true, null);
    }

    public static SearchWeatherResult result(Exception error) {
        return new SearchWeatherResult(null, null, false, error);
    }

    private SearchWeatherResult(Integer locationId, SearchWeatherForCoordinates weather, boolean successful, Exception error) {
        this.locationId = locationId;
        this.weather = weather;
        this.successful = successful;
        this.exception = error;
    }

    public boolean isSuccessful() {
        return successful;
    }

}
