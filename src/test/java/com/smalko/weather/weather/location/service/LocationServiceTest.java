package com.smalko.weather.weather.location.service;

import com.smalko.weather.weather.location.dto.CreateLocationDto;
import com.smalko.weather.weather.user.UsersService;
import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.user.result.RegistrationResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationServiceTest {

    @Test
    void saveLocationInUser() {
        var smalko = CreateUsersDto.builder()
                .name("Smalko")
                .password("11111")
                .build();
        UsersService.getInstance().registrationUser(smalko);

        var locationDto = CreateLocationDto.builder()
                .latitude(40.1)
                .name("Londo")
                .longitude(33.1)
                .userId(1)
                .build();

        LocationService.getInstance().saveLocationInUser(locationDto);
    }
}