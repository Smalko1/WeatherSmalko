package com.smalko.weather.weather.user;

import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.user.mapper.CreateUserMapper;
import com.smalko.weather.weather.user.mapper.ReadUserMapper;
import com.smalko.weather.weather.user.validator.CreateUsersValidator;
import com.smalko.weather.weather.util.PasswordHashing;

public class UsersService {
    private static final UsersService INSTANCE = new UsersService();
    private static final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private static final ReadUserMapper readUserMapper = ReadUserMapper.getInstance();
    private static final CreateUsersValidator validator = CreateUsersValidator.getInstance();

    public void createUser(CreateUsersDto users){

        //validator
        //HashPassword
        users.setPassword(PasswordHashing.hashPassword(users.getPassword()));
        //map
        var usersEntity = createUserMapper.mapFrom(users);
        //UsersRepository.getInstance().save(usersEntity);
        //usersRepository.save

    }



    public static UsersService getInstance() {
        return INSTANCE;
    }
}
