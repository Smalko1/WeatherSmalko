package com.smalko.weather.weather.location.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadSearchCityDto {
    private String name;
    private Double lat;
    private Double lon;
    private String country;
}
