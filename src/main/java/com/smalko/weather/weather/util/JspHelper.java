package com.smalko.weather.weather.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspHelper {
    private static final String path = "WEB-INF/jsp/%s.jsp";

    public static String gatPath(String name){
        return path.formatted(name);
    }
}
