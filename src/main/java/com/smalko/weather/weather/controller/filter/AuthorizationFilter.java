package com.smalko.weather.weather.controller.filter;

import com.smalko.weather.weather.session.SessionService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
@WebFilter("/*")
public class AuthorizationFilter implements Filter {
    private static final String ATTRIBUTE_USER_ID = "userId";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var request = (HttpServletRequest) servletRequest;
        isUserLoggedIn(request);
        filterChain.doFilter(request, servletResponse);
    }

    private void isUserLoggedIn(HttpServletRequest request) {
        var userId = (Integer) request.getSession().getAttribute(ATTRIBUTE_USER_ID);
        if (userId == null) {
            var sessionIdInCookies = findSessionIdInCookies(request);
            if (sessionIdInCookies != null) {
                var sessionResult = SessionService.getInstance().getSessionById(Integer.parseInt(sessionIdInCookies));
                if (sessionResult.isSuccessful()) {
                    var session = request.getSession();
                    session.setAttribute("userId", sessionResult.getReadUserDto().getId());
                }
            }
        }
    }

    private String findSessionIdInCookies(HttpServletRequest request) {
        var cookies = request.getCookies();
        String sessionId = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("sessionId".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }
        return sessionId;
    }
}