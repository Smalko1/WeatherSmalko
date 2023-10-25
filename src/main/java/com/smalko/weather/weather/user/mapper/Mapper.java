package com.smalko.weather.weather.user.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
