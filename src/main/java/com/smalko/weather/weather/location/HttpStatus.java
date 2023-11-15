package com.smalko.weather.weather.location;

public enum HttpStatus {
    HTTP_OK("200Ok"),
    HTTP_BAD_REQUEST("400"),
    HTTP_UNAUTHORIZED("401"),
    HTTP_NOT_FOUND("404"),
    HTTP_MANY_REQUESTS("429");

    private final String message;

    HttpStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
