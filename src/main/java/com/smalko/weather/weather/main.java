package com.smalko.weather.weather;


import com.smalko.weather.weather.location.json.SearchCity;
import com.smalko.weather.weather.location.result.api.SearchCityResult;
import com.smalko.weather.weather.location.result.api.SearchWeatherResult;
import com.smalko.weather.weather.location.service.OpenWeatherAPI;

public class main {
    public static void main(String[] args) {
        var searchCityResult = OpenWeatherAPI.requestWeatherByCity("London");

        for (SearchCity searchCity : searchCityResult.getSearchCity()) {
            System.out.println(searchCity.toString());
        }
        var searchCity = searchCityResult.getSearchCity().get(0);

        var searchWeatherResult = OpenWeatherAPI.requestWeatherByCoordinate(searchCity.getLat(), searchCity.getLon());
        System.out.println(searchWeatherResult.getWeather());
    }
}
