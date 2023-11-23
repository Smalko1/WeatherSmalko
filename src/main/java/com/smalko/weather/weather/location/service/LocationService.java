package com.smalko.weather.weather.location.service;

import com.smalko.weather.weather.location.dto.CreateLocationDto;
import com.smalko.weather.weather.location.errors.NotFoundExceptionWeather;
import com.smalko.weather.weather.location.errors.ResponseExceptionWeather;
import com.smalko.weather.weather.location.errors.TooManyRequestsExceptionWeather;
import com.smalko.weather.weather.location.json.SearchCityList;
import com.smalko.weather.weather.location.json.SearchWeatherForCoordinates;
import com.smalko.weather.weather.location.result.SearchWeatherResult;

import java.util.ArrayList;
import java.util.List;


public class LocationService {
    private static final LocationService INSTANCE = new LocationService();



    public void saveLocation(CreateLocationDto location) {

    }

    public static LocationService getInstance() {
        return INSTANCE;
    }
}
