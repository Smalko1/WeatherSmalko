package com.smalko.weather.weather.user.mapper;

import com.smalko.weather.weather.user.UsersEntity;
import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.user.dto.ReadUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "username", target = "name")
    ReadUserDto userToUserDto(UsersEntity user);
    @Mapping(target = "locations", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "username")
    UsersEntity userToUserEntity(CreateUsersDto createUsersDto);
}
