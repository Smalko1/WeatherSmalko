package com.smalko.weather.weather.controller.servlet;

import com.smalko.weather.weather.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "logout", value = UrlPath.LOGOUT)
public class LogoutServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        cookie(response);
        response.sendRedirect(request.getContextPath() + UrlPath.LOGIN);
    }

    private static void cookie(HttpServletResponse response) {
        var sessionCookie = new Cookie("sessionId", null);
        sessionCookie.setMaxAge(0);
        response.addCookie(sessionCookie);
    }
}
