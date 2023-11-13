package com.smalko.weather.weather.location.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchCity {
    private String name;
    private Double lat;
    private Double lon;
    private String country;

}
