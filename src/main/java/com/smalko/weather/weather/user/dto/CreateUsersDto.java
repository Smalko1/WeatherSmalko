package com.smalko.weather.weather.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUsersDto{
    private String name;
    private String password;
}
