package com.smalko.weather.weather.user;

import com.smalko.weather.weather.user.validator.Error;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Result {
    private List<Error> errors;

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
