package com.smalko.weather.weather.location.result.api;

import com.smalko.weather.weather.location.HttpStatus;
import com.smalko.weather.weather.location.json.SearchCity;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class SearchCityResult {

    private final List<SearchCity> searchCity;
    private final HttpStatus status;

    public static SearchCityResult createSearchCityResult(List<SearchCity> searchCity){
        return new SearchCityResult(searchCity, HttpStatus.HTTP_OK);
    }

    public static SearchCityResult createSearchCityResult(HttpStatus status){
        return new SearchCityResult(Collections.emptyList(), status);
    }

    private SearchCityResult(List<SearchCity> searchCity, HttpStatus status) {
        this.searchCity = searchCity;
        this.status = status;
    }
}
