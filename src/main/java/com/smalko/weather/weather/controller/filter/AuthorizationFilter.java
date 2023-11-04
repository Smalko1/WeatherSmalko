package com.smalko.weather.weather.controller.filter;

import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.user.dto.ReadUserDto;
import com.smalko.weather.weather.util.UrlPath;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

import static com.smalko.weather.weather.util.UrlPath.*;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {
    private static final Set<String> PUBLIC_PATH = Set.of(REGISTRATION, LOGIN, HOME);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var uri = ((HttpServletRequest) servletRequest).getRequestURI();
        if (isPublicPath(uri) || isUserLoggedIn(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            reject(servletRequest, servletResponse);
        }
    }

    private boolean isUserLoggedIn(ServletRequest servletRequest) {
        var user = (ReadUserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return user != null;
    }

    private boolean isPublicPath(String uri) {
        return PUBLIC_PATH.stream().anyMatch(uri::startsWith);
    }

    private void reject(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        var prevPage = ((HttpServletRequest) servletRequest).getHeader("referer");
        ((HttpServletResponse) servletResponse).sendRedirect(prevPage != null ? prevPage : LOGIN);
    }
}
