package com.smalko.weather.weather.location.json;

import lombok.Data;

@Data
public class SearchCity {
    private String name;
    private Double lat;
    private Double lon;
    private String country;


}
