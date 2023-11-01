package com.smalko.weather.weather.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
public class CreateUsersDto{
    private String name;
    private String password;
}
