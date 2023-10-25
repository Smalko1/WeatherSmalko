package com.smalko.weather.weather.user;

import com.smalko.weather.weather.user.dto.CreateUsersDto;

public class UsersService {


    public void createUser(CreateUsersDto users){
        //validator
        //map
        //usersRepository.save
    }

    public boolean isAvailability(String name){
        //Проверка на занятость имени, если такой пользователь существует то true, если имя свободное false
        return false;
    }
}
