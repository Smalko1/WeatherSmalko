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

    public SearchWeatherResult searchWeatherByCity(String city) {
        List<SearchWeatherForCoordinates> weatherForCity = new ArrayList<>();
        try {
            var searchCityLists = OpenWeatherAPI.requestWeatherByCity(city);
            if (!searchCityLists.isEmpty()) {

                for (SearchCityList searchCity : searchCityLists) {
                    var searchWeather = OpenWeatherAPI.requestWeatherByCoordinate(searchCity.getLat(), searchCity.getLon());
                    searchWeather.setCityName(searchCity.getName() + " - " + searchCity.getCountry());
                    weatherForCity.add(searchWeather);
                }
            }
        }catch (NotFoundExceptionWeather | ResponseExceptionWeather | TooManyRequestsExceptionWeather e){
            return  SearchWeatherResult.result(e);
        }
        return SearchWeatherResult.result(weatherForCity);
    }

    public void saveLocation(CreateLocationDto location) {

    }

    public static LocationService getInstance() {
        return INSTANCE;
    }
}
