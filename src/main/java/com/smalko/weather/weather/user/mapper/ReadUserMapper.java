package com.smalko.weather.weather.user.mapper;

import com.smalko.weather.weather.user.UsersEntity;
import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.user.dto.ReadUserDto;

public class ReadUserMapper implements Mapper<UsersEntity, ReadUserDto>{

    private static final ReadUserMapper INSTANCE = new ReadUserMapper();


    @Override
    public ReadUserDto mapFrom(UsersEntity object) {
        return null;
    }

    public static ReadUserMapper getInstance() {
        return INSTANCE;
    }
}
