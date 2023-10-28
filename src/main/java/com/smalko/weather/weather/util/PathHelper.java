package com.smalko.weather.weather.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PathHelper {
    private static final String path = "WEB-INF/templates/%s.html";

    public static String gatPath(String name){
        return path.formatted(name);
    }
}
