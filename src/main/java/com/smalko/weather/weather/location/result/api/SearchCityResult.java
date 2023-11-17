package com.smalko.weather.weather.location.result.api;

import com.smalko.weather.weather.location.HttpStatus;
import com.smalko.weather.weather.location.json.SearchCityList;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
@EqualsAndHashCode
public class SearchCityResult {

    private final List<SearchCityList> searchCityList;
    private final HttpStatus status;

    public static SearchCityResult createSearchCityResult(List<SearchCityList> searchCityList){
        return new SearchCityResult(searchCityList, HttpStatus.HTTP_OK);
    }

    public static SearchCityResult createSearchCityResult(HttpStatus status){
        return new SearchCityResult(Collections.emptyList(), status);
    }

    private SearchCityResult(List<SearchCityList> searchCityList, HttpStatus status) {
        this.searchCityList = searchCityList;
        this.status = status;
    }
}
