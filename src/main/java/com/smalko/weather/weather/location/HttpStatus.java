package com.smalko.weather.weather.location;

public enum HttpStatus {
    HTTP_OK,
    HTTP_BAD_REQUEST,
    HTTP_UNAUTHORIZED,
    HTTP_NOT_FOUND,
    HTTP_MANY_REQUESTS;

    public boolean isOK() {
        return this == HTTP_OK;
    }
}
