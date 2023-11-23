package com.smalko.weather.weather.location.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateLocationDto {

    private String name;
    private Double latitude;
    private Double longitude;
}
