package com.smalko.weather.weather.controller.servlet;

import com.smalko.weather.weather.session.SessionService;
import com.smalko.weather.weather.user.UsersService;
import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.user.result.LoginResult;
import com.smalko.weather.weather.util.UrlPath;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.Duration;

@WebServlet(name = "LoginServlet", value = UrlPath.LOGIN)
public class LoginServlet extends BaseServlet {
    private static final Duration ONE_DAY = Duration.ofDays(1);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        super.processTemplate("login", request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);

        var username = request.getParameter("username");
        var password = request.getParameter("password");
        if (username != null && password != null) {
            var userLogin = CreateUsersDto.builder()
                    .name(username)
                    .password(password)
                    .build();

            var loginResult = UsersService.getInstance().authenticationUser(userLogin);
            if (loginResult.isSuccess()) {
                var session = request.getSession();
                session.setAttribute("userId", loginResult.getUser().getId());
                createCookie(response, loginResult);
                putAttributeInModel("errors", loginResult.getErrors());
                response.sendRedirect(UrlPath.HOME);
            } else {
                putAttributeInModel("errors", loginResult.getErrors());
                super.processTemplate("login", request, response);
            }
            return;
        }
        doGet(request, response);
    }

    private static void createCookie(HttpServletResponse response, LoginResult loginResult) {
        var sessionResult = SessionService.getInstance().saveSession(loginResult.getUser());
        if (sessionResult.isSuccessful()) {
            var sessionCookie = new Cookie("sessionId", sessionResult.getReadSessionDto().getId().toString());
            sessionCookie.setMaxAge((int) ONE_DAY.toSeconds());
            response.addCookie(sessionCookie);
        }
    }
}
