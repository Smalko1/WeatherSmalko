package com.smalko.weather.weather.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
@Builder
public class ReadUserDto{
    private Integer id;
    private String name;
}
