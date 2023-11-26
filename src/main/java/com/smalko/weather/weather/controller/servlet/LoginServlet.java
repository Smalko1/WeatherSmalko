package com.smalko.weather.weather.controller.servlet;

import com.smalko.weather.weather.session.SessionService;
import com.smalko.weather.weather.user.UsersService;
import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.user.result.LoginResult;
import com.smalko.weather.weather.user.validator.Error;
import com.smalko.weather.weather.util.UrlPath;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

@WebServlet(name = "LoginServlet", value = UrlPath.LOGIN)
public class LoginServlet extends BaseServlet {
    private static final Duration ONE_DAY = Duration.ofDays(1);
    private static final String ATTRIBUTE_ERROR = "errors";
    private static final String ATTRIBUTE_USER_ID = "userId";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);

        var errors = (String) request.getSession().getAttribute(ATTRIBUTE_ERROR);
        if (errors != null){
            putAttributeInModel("errors", errors);
            request.getSession().removeAttribute(ATTRIBUTE_ERROR);
        }

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
                session.setAttribute(ATTRIBUTE_USER_ID, loginResult.getUser().getId());
                createCookie(response, loginResult);
                response.sendRedirect(UrlPath.HOME);
            } else {
                request.getSession().setAttribute(ATTRIBUTE_ERROR, loginResult.getErrors());
            }
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
