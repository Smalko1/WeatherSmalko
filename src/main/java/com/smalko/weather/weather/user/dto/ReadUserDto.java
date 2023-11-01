package com.smalko.weather.weather.user.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ReadUserDto{
    Integer id;
    String name;
}
