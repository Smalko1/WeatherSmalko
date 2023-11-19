package com.smalko.weather.weather.location.errors;

public class TooManyRequestsExceptionWeather extends RuntimeException{

    public TooManyRequestsExceptionWeather() {
        super("Key quota of requests to this API has been exceeded");
    }

}
