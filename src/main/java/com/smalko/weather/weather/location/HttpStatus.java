package com.smalko.weather.weather.location;

public enum HttpStatus {
    HTTP_OK(200),
    HTTP_BAD_REQUEST(400),
    HTTP_UNAUTHORIZED(401),
    HTTP_NOT_FOUND(404),
    HTTP_MANY_REQUESTS(429);

    private final int code;

    HttpStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
