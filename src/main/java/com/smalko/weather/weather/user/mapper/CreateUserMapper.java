package com.smalko.weather.weather.user.mapper;

import com.smalko.weather.weather.user.UsersEntity;
import com.smalko.weather.weather.user.dto.CreateUsersDto;

public class CreateUserMapper implements Mapper<CreateUsersDto, UsersEntity>{

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();
    @Override
    public UsersEntity mapFrom(CreateUsersDto object) {
        return UsersEntity.builder()
                .username(object.name())
                .password(object.password())
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
