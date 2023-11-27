package com.smalko.weather.weather.controller.filter;

import com.smalko.weather.weather.session.SessionService;
import com.smalko.weather.weather.session.result.GetSessionResult;
import com.smalko.weather.weather.user.dto.ReadUserDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

import static com.smalko.weather.weather.util.UrlPath.*;

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