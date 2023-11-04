package com.smalko.weather.weather.user;

import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.util.HibernateUtil;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

class UsersServiceTest {
    private static final UsersService USERS_SERVICE = UsersService.getInstance();
    private static CreateUsersDto createUsersDto;

    @BeforeAll
    static void setCreateUsersDto(){
        createUsersDto = CreateUsersDto.builder()
                .name("Smalko")
                .password("123")
                .build();
    }

    @Test
    void registrationUserIsSuccessful() {
        var resultRegistrationUser = USERS_SERVICE.registrationUser(createUsersDto);

        assertThat(resultRegistrationUser.isSuccess()).isTrue();
    }

    @Test
    void registrationUserIsUnsuccessfulNoValid() {

        var isNotValidUserDto = CreateUsersDto.builder()
                .name("")
                .password("")
                .build();
        var resultRegistrationUser = USERS_SERVICE.registrationUser(isNotValidUserDto);

        assertThat(resultRegistrationUser.isSuccess()).isFalse();
        assertThat(resultRegistrationUser.getErrors().size()).isEqualTo(2);
    }

    @Test
    void registrationUserIsUnsuccessfulNoUnique(){

        USERS_SERVICE.registrationUser(createUsersDto);
        var resultRegistrationUser = USERS_SERVICE.registrationUser(createUsersDto);

        assertThat(resultRegistrationUser.isSuccess()).isFalse();
    }


    @Test
    void authenticationUserSuccessful(){
        var smalko = CreateUsersDto.builder()
                .name("Smalko")
                .password("1234")
                .build();
        var smalko1 = CreateUsersDto.builder()
                .name("Smalko")
                .password("1234")
                .build();
        var resultRegistrationUser = USERS_SERVICE.registrationUser(smalko);
        var resultAuthenticationUser = USERS_SERVICE.authenticationUser(smalko1);

        assertThat(resultRegistrationUser.isSuccess()).isTrue();
        assertThat(resultAuthenticationUser.isSuccess()).isTrue();
    }

    @Test
    void authenticationUserUnSuccessfulInCorrectName(){
        var smalko = CreateUsersDto.builder()
                .name("Smalko")
                .password("1234")
                .build();
        var smalko1 = CreateUsersDto.builder()
                .name("Smalko1")
                .password("1234")
                .build();
        var resultRegistrationUser = USERS_SERVICE.registrationUser(smalko);
        var resultAuthenticationUser = USERS_SERVICE.authenticationUser(smalko1);

        assertThat(resultRegistrationUser.isSuccess()).isTrue();
        assertThat(resultAuthenticationUser.isSuccess()).isFalse();
    }

    @Test
    void authenticationUserUnSuccessfulByDifferentPasswords(){
        var smalko = CreateUsersDto.builder()
                .name("Smalko")
                .password("1234")
                .build();
        var smalko1 = CreateUsersDto.builder()
                .name("Smalko")
                .password("1235")
                .build();
        var resultRegistrationUser = USERS_SERVICE.registrationUser(smalko);
        var resultAuthenticationUser = USERS_SERVICE.authenticationUser(smalko1);

        assertThat(resultRegistrationUser.isSuccess()).isTrue();
        assertThat(resultAuthenticationUser.isSuccess()).isFalse();
    }
}