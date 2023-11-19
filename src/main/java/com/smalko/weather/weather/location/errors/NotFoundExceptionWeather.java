package com.smalko.weather.weather.location.errors;

public class NotFoundExceptionWeather extends RuntimeException{

    public NotFoundExceptionWeather() {
        super("Error 404 - Not Found. You can get 404 error if data with requested parameters (lat, lon, date etc) does not exist in service database. You must not retry the same request.");
    }
}
