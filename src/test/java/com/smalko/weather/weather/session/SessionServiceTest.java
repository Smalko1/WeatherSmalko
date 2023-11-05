package com.smalko.weather.weather.session;

import com.smalko.weather.weather.session.result.SaveSessionResult;
import com.smalko.weather.weather.user.UsersService;
import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.user.dto.ReadUserDto;
import com.smalko.weather.weather.user.result.RegistrationResult;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SessionServiceTest {
    private static final SessionService SESSION_SERVICE = SessionService.getInstance();
    private static final UsersService USERS_SERVICE = UsersService.getInstance();

    @Test
    void createSession() {
        var smalko = CreateUsersDto.builder()
                .name("Smalko")
                .password("1234")
                .build();
        var smalko1 = CreateUsersDto.builder()
                .name("Smalko")
                .password("1234")
                .build();
        var registrationResult = USERS_SERVICE.registrationUser(smalko);
        var resultAuthenticationUser = USERS_SERVICE.authenticationUser(smalko1);

        var result = SESSION_SERVICE.saveSession(resultAuthenticationUser.getUser());

        assertThat(registrationResult.isSuccess()).isTrue();
        assertThat(resultAuthenticationUser.isSuccess()).isTrue();
        assertThat(result.isSuccessful()).isTrue();

    }
}