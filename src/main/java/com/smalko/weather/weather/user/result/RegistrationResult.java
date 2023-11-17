package com.smalko.weather.weather.user.result;


import com.smalko.weather.weather.user.validator.Error;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
@Getter
@EqualsAndHashCode
public class RegistrationResult {
    private final boolean success;
    private final List<Error> errors;


    public static RegistrationResult result(List<Error> errors) {
        return errors.isEmpty() ? new RegistrationResult(true, Collections.emptyList()) : new RegistrationResult(false, errors);
    }

    private RegistrationResult(boolean success, List<Error> errors) {
        this.success = success;
        this.errors = errors;
    }

}
