package com.smalko.weather.weather.user.result;

import com.smalko.weather.weather.user.dto.ReadUserDto;
import com.smalko.weather.weather.user.validator.Error;

import java.util.Collections;
import java.util.List;

public class LoginResult {
    private boolean success;
    private ReadUserDto user;
    private List<Error> errors;



    public static LoginResult result(ReadUserDto user, List<Error> errors) {
        return errors.isEmpty() ? new LoginResult(true, user, Collections.emptyList()) : new LoginResult(false, user, errors);
    }

    private LoginResult(boolean success, ReadUserDto user, List<Error> errors) {
        this.success = success;
        this.user = user;
        this.errors = errors;
    }

    public boolean isSuccess() {
        return success;
    }

    public ReadUserDto getUser() {
        return user;
    }

    public List<Error> getErrors() {
        return errors;
    }
}
