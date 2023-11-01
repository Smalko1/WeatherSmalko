package com.smalko.weather.weather.user.validator;

import com.smalko.weather.weather.user.dto.CreateUsersDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CreateUsersValidatorTest {

    @Test
    void isValid() {
        var usersDto = CreateUsersDto.builder()
                .name("Smalko")
                .password("1234")
                .build();

        var valid = CreateUsersValidator.getInstance().isValid(usersDto);

        assertThat(valid.isValid()).isTrue();
    }

    @Test
    void isNotValidName(){
        var usersDto = CreateUsersDto.builder()
                .name("Sm")
                .password("1234")
                .build();

        var valid = CreateUsersValidator.getInstance().isValid(usersDto);

        assertThat(valid.isValid()).isFalse();
        assertThat(valid.getErrors().size()).isEqualTo(1);
    }

    @Test
    void isNotValidPassword(){
        var usersDto = CreateUsersDto.builder()
                .name("Smalko")
                .password("")
                .build();

        var valid = CreateUsersValidator.getInstance().isValid(usersDto);

        assertThat(valid.isValid()).isFalse();
        assertThat(valid.getErrors().size()).isEqualTo(1);
    }

    @Test
    void isNotValidPasswordAndName(){
        var usersDto = CreateUsersDto.builder()
                .name("")
                .password("")
                .build();

        var valid = CreateUsersValidator.getInstance().isValid(usersDto);

        assertThat(valid.isValid()).isFalse();
        assertThat(valid.getErrors().size()).isEqualTo(2);
    }
}