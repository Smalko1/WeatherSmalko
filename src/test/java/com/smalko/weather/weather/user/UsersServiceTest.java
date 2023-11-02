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

        assertThat(resultRegistrationUser.hasErrors()).isFalse();
    }

    @Test
    void registrationUserIsUnsuccessfulNoValid() {

        var isNotValidUserDto = CreateUsersDto.builder()
                .name("")
                .password("")
                .build();
        var resultRegistrationUser = USERS_SERVICE.registrationUser(isNotValidUserDto);

        assertThat(resultRegistrationUser.hasErrors()).isTrue();
        assertThat(resultRegistrationUser.getErrors().size()).isEqualTo(2);
    }

    @Test
    void registrationUserIsUnsuccessfulNoUnique(){

        USERS_SERVICE.registrationUser(createUsersDto);
        var resultRegistrationUser = USERS_SERVICE.registrationUser(createUsersDto);

        assertThat(resultRegistrationUser.hasErrors()).isTrue();
    }
}