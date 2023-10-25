package com.smalko.weather.weather.user.mapper;

import com.smalko.weather.weather.user.UsersEntity;
import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.user.dto.ReadUserDto;

public class ReadUserMapper implements Mapper<UsersEntity, ReadUserDto>{

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();


    @Override
    public ReadUserDto mapFrom(UsersEntity object) {
        return null;
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
