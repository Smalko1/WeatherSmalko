package com.smalko.weather.weather.location.service;

import com.smalko.weather.weather.location.result.SearchWeatherResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OpenWeatherAPITest {

    @Test
    void requestWeather() {
        var searchCityResult = OpenWeatherAPI.requestWeatherByCity("Kiev").getSearchCityLists().get(0);
        var searchWeatherResult = OpenWeatherAPI.requestWeather(searchCityResult.getLat(), searchCityResult.getLon(), searchCityResult.getName());

        assertThat(searchWeatherResult).isNotNull();
    }

    @Test
    void requestWeatherByCity() {
        var searchCityResult = OpenWeatherAPI.requestWeatherByCity("Kiev");

        assertThat(searchCityResult).isNotNull();
    }
}