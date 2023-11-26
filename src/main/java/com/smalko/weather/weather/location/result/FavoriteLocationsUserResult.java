package com.smalko.weather.weather.location.result;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FavoriteLocationsUserResult {
    private final List<SearchWeatherResult> searchWeatherResultsSuccessful;
    private final List<SearchWeatherResult> searchWeatherResultsUnSuccessful;
    private boolean successful = true;

    public void addWeatherResult(Integer locationId, SearchWeatherResult weatherResult){
        if (weatherResult.isSuccessful()){
            weatherResult.setLocationId(locationId);
            searchWeatherResultsSuccessful.add(weatherResult);
        }else {
            searchWeatherResultsUnSuccessful.add(weatherResult);
        }
    };

    public FavoriteLocationsUserResult() {
        this.searchWeatherResultsSuccessful = new ArrayList<>();
        this.searchWeatherResultsUnSuccessful = new ArrayList<>();
    }
}
