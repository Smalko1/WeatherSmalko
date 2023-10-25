package com.smalko.weather.weather.user.validator;

public interface Validator<T> {

    ValidationResult isValid(T object);
}
