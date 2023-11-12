package com.smalko.weather.weather.location.result.api;

import com.smalko.weather.weather.location.HttpStatus;
import com.smalko.weather.weather.location.json.SearchCity;
import lombok.Getter;

@Getter
public class SearchCityResult {

    private final SearchCity searchCity;
    private final HttpStatus status;

    public static void searchCityResult(SearchCity searchCity){
        new SearchCityResult(searchCity, HttpStatus.HTTP_OK);
    }

    public static void searchCityResult(HttpStatus status){
        new SearchCityResult(null, status);
    }

    private SearchCityResult(SearchCity searchCity, HttpStatus status) {
        this.searchCity = searchCity;
        this.status = status;
    }
}
