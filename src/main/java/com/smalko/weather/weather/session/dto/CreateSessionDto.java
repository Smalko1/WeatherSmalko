package com.smalko.weather.weather.session.dto;

import com.smalko.weather.weather.user.UsersEntity;
import com.smalko.weather.weather.user.dto.ReadUserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSessionDto {
    private UsersEntity usersEntity;
}
