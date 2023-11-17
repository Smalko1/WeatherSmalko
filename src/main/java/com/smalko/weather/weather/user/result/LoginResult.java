package com.smalko.weather.weather.user.result;

import com.smalko.weather.weather.user.dto.ReadUserDto;
import com.smalko.weather.weather.user.validator.Error;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
@EqualsAndHashCode
public class LoginResult {
    private final boolean success;
    private final ReadUserDto user;
    private final List<Error> errors;

    public static LoginResult result(ReadUserDto user, List<Error> errors) {
        return errors.isEmpty() ? new LoginResult(true, user, Collections.emptyList()) : new LoginResult(false, user, errors);
    }

    private LoginResult(boolean success, ReadUserDto user, List<Error> errors) {
        this.success = success;
        this.user = user;
        this.errors = errors;
    }
}
