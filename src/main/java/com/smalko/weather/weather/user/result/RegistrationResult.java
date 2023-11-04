package com.smalko.weather.weather.user.result;


import com.smalko.weather.weather.user.validator.Error;

import java.util.Collections;
import java.util.List;

public class RegistrationResult {
    private boolean success;
    private List<Error> errors;


    public static RegistrationResult result(List<Error> errors) {
        return errors.isEmpty() ? new RegistrationResult(true, Collections.emptyList()) : new RegistrationResult(false, errors);
    }

    private RegistrationResult(boolean success, List<Error> errors) {
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
