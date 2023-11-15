package com.smalko.weather.weather.location.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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