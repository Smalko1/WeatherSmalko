package com.smalko.weather.weather.controller.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.smalko.weather.weather.util.UrlPath.LOGIN;
import static com.smalko.weather.weather.util.UrlPath.LOGOUT;

@WebServlet(name = "logout", value = LOGOUT)
public class LogoutServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        cookie(response);
        response.sendRedirect(request.getContextPath() + LOGIN);
    }

    private static void cookie(HttpServletResponse response) {
        var sessionCookie = new Cookie("sessionId", null);
        sessionCookie.setMaxAge(0);
        response.addCookie(sessionCookie);
    }
}
