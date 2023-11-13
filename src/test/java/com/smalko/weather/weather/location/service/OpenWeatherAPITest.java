package com.smalko.weather.weather.location.service;

import com.smalko.weather.weather.location.result.api.SearchCityResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenWeatherAPITest {

    @Test
    void requestWeatherByCoordinate() {
    }

    @Test
    void requestWeatherByCity() {
        var searchCityResult = OpenWeatherAPI.requestWeatherByCity("Kiev");

        Assertions.assertThat(searchCityResult).isNotNull();
    }
}