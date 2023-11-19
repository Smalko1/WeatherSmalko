package com.smalko.weather.weather.location.errors;

public class ResponseExceptionWeather extends RuntimeException{
    public ResponseExceptionWeather() {
        super("Some mandatory parameters are missing in the query, or some query parameters have an incorrect format or values outside the allowed range.");
    }

}
