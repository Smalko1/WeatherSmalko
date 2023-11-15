package com.smalko.weather.weather.controller.filter;

import com.smalko.weather.weather.session.SessionService;
import com.smalko.weather.weather.user.dto.ReadUserDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.Cookie;
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
        if (isUserLoggedIn(servletRequest) || isPublicPath(uri)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            reject(servletRequest, servletResponse);
        }
    }

    private boolean isUserLoggedIn(ServletRequest servletRequest) {
        var user = (ReadUserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        if (user == null) {
            String sessionId = findSessionIdInCookies((HttpServletRequest) servletRequest);
            if (sessionId != null){
                var result = SessionService.getInstance().getSessionById(Integer.parseInt(sessionId));
                if (result.isSuccessful()) {
                    var session = ((HttpServletRequest) servletRequest).getSession();
                    session.setAttribute("userId", result.getReadUserDto().getId());
                    return true;
                }
            }
        }
        return false;
    }

    private static String findSessionIdInCookies(HttpServletRequest servletRequest) {
        var cookies = servletRequest.getCookies();
        String sessionId = null;
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if ("sessionId".equals(cookie.getName())){
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }
        return sessionId;
    }

    private boolean isPublicPath(String uri) {
        return PUBLIC_PATH.stream().anyMatch(uri::startsWith);
    }

    private void reject(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        var prevPage = ((HttpServletRequest) servletRequest).getHeader("referer");
        ((HttpServletResponse) servletResponse).sendRedirect(prevPage != null ? prevPage : LOGIN);
    }
}
