package com.smalko.weather.weather.controller.servlet;

import com.smalko.weather.weather.util.UrlPath;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "logout", value = UrlPath.LOGOUT)
public class LogoutServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        cookie(response);
        response.sendRedirect(UrlPath.LOGIN);
    }

    // TODO: 27.11.2023 Не перенаправляет на логин сттаницу 

    private static void cookie(HttpServletResponse response) {
        var sessionCookie = new Cookie("sessionId", null);
        sessionCookie.setMaxAge(0);
        response.addCookie(sessionCookie);
    }
}
