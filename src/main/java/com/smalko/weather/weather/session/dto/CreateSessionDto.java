package com.smalko.weather.weather.session.dto;

import com.smalko.weather.weather.user.UsersEntity;
import com.smalko.weather.weather.user.dto.ReadUserDto;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;

@Data
public class CreateSessionDto {
    private static final Duration ONE_DAY = Duration.ofDays(1);

    private UsersEntity usersEntity;
    private LocalDateTime expiresAt;

    public CreateSessionDto(UsersEntity usersEntity) {
        this.usersEntity = usersEntity;
        expiresAt = LocalDateTime.now().plus(ONE_DAY);
    }
}
