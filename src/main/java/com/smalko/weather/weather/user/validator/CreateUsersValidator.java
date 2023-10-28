package com.smalko.weather.weather.user.validator;

import com.smalko.weather.weather.user.dto.CreateUsersDto;

public class CreateUsersValidator implements Validator<CreateUsersDto>{
    private static final CreateUsersValidator INSTANCE = new CreateUsersValidator();
    @Override
    public ValidationResult isValid(CreateUsersDto object) {
        var validationResult = new ValidationResult();
        if (object.getName().length() < 3 || object.getName().length() > 20){
            validationResult.add(Error.of("IncorrectLength", "Incorrect name length"));
        }else if (object.getPassword().length() < 3){
            validationResult.add(Error.of("LittlePassword", "This password is little"));
        }
        return validationResult;
    }

    public static CreateUsersValidator getInstance() {
        return INSTANCE;
    }
}
