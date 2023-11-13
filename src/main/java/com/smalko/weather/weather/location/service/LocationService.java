package com.smalko.weather.weather.location.service;

import com.smalko.weather.weather.location.HttpStatus;
import com.smalko.weather.weather.location.json.SearchCity;
import com.smalko.weather.weather.location.result.api.SearchCityResult;

import java.util.List;

public class LocationService {

    public void searchCity(String city){
        var searchCityResult = OpenWeatherAPI.requestWeatherByCity(city);

        if (searchCityResult.getStatus().equals(HttpStatus.HTTP_OK)){
            var searchCity = searchCityResult.getSearchCity();
        }
    }
}
