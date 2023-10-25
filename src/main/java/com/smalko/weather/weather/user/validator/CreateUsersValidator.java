package com.smalko.weather.weather.user.validator;

import com.smalko.weather.weather.user.dto.CreateUsersDto;

public class CreateUsersValidator implements Validator<CreateUsersDto>{
    private static final CreateUsersValidator INSTANCE = new CreateUsersValidator();
    @Override
    public ValidationResult isValid(CreateUsersDto object) {
        var validationResult = new ValidationResult();

        return validationResult;
    }

    public static CreateUsersValidator getInstance() {
        return INSTANCE;
    }
}
