package com.smalko.weather.weather.user;


import com.smalko.weather.weather.user.validator.Error;

import java.util.Collections;
import java.util.List;

public class Result {
    private boolean success;
    private List<Error> errors;


    public static Result result(List<Error> errors) {
        return errors.isEmpty() ? new Result(true, Collections.emptyList()) : new Result(false, errors);
    }

    private Result(boolean success, List<Error> errors) {
        this.success = success;
        this.errors = errors;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<Error> getErrors() {
        return errors;
    }
}
