package com.smalko.weather.weather.user.mapper;

import com.smalko.weather.weather.user.UsersEntity;
import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.user.dto.ReadUserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void userToUserDto() {
        UsersEntity entity = UsersEntity.builder()
                .id(1)
                .username("Smalko")
                .build();

        var readUserDto = UserMapper.INSTANCE.userToUserDto(entity);

        assertThat(readUserDto).isNotNull();
        assertThat(readUserDto.getName()).isEqualTo(entity.getUsername());

    }

    @Test
    void userToUserEntity() {
        CreateUsersDto usersDto = CreateUsersDto.builder()
                .name("Smalko")
                .password("123")
                .build();

        var entity = UserMapper.INSTANCE.userToUserEntity(usersDto);

        assertThat(entity).isNotNull();
        assertThat(entity.getUsername()).isEqualTo(usersDto.getName());
        assertThat(entity.getPassword()).isEqualTo(usersDto.getPassword());
    }
}