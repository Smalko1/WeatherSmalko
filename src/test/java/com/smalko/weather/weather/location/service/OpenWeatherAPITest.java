package com.smalko.weather.weather.location.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OpenWeatherAPITest {

    @Test
    void requestWeather() {
        var searchCityResult = OpenWeatherAPI.requestCoordinatesByCity("Kiev").getSearchCityLists().get(0);
        var searchWeatherResult = OpenWeatherAPI.requestWeather(searchCityResult.getLat(), searchCityResult.getLon(), searchCityResult.getName());

        assertThat(searchWeatherResult).isNotNull();
    }

    @Test
    void requestWeatherByCity() {
        var searchCityResult = OpenWeatherAPI.requestCoordinatesByCity("Kiev");

        assertThat(searchCityResult).isNotNull();
    }
}