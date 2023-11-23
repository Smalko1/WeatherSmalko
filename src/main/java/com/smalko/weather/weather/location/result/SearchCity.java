package com.smalko.weather.weather.location.result;

import com.smalko.weather.weather.location.json.SearchCityList;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
@Getter
public class SearchCity {
    private final List<SearchCityList> searchCityLists;
    private final Exception exception;
    private final boolean successful;

    public static SearchCity result(List<SearchCityList> searchCityLists){
        return new SearchCity(searchCityLists, null, true);
    }

    public static SearchCity result(Exception exception){
        return new SearchCity(Collections.emptyList(), exception, false);
    }

    private SearchCity(List<SearchCityList> searchCityLists, Exception exception, boolean successful) {
        this.searchCityLists = searchCityLists;
        this.exception = exception;
        this.successful = successful;
    }
}
