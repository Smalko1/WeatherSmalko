package com.smalko.weather.weather.user;

import com.smalko.weather.weather.user.dto.CreateUsersDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UsersServiceTest {
    private static final UsersService USERS_SERVICE = UsersService.getInstance();

    @Test
    void createUserIsSuccessful() {
        var createUsersDto = CreateUsersDto.builder()
                .name("Smalko")
                .password("123")
                .build();

        var user = USERS_SERVICE.createUser(createUsersDto);

        assertThat(user.hasErrors()).isFalse();
    }

    @Test
    void createUserIsUnsuccessfulNoValid() {
        var createUsersDto = CreateUsersDto.builder()
                .name("")
                .password("123")
                .build();

        var user = USERS_SERVICE.createUser(createUsersDto);

        assertThat(user.hasErrors()).isTrue();
    }
}