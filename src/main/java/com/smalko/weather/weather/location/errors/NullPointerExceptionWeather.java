package com.smalko.weather.weather.location.errors;

public class NullPointerExceptionWeather extends RuntimeException{

    public NullPointerExceptionWeather() {
        super("An error occurred while searching for your city");
    }
}
