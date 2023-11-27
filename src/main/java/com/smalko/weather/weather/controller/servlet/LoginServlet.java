package com.smalko.weather.weather.controller.servlet;

import com.smalko.weather.weather.session.SessionService;
import com.smalko.weather.weather.user.UsersService;
import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.user.result.LoginResult;
import com.smalko.weather.weather.user.validator.Error;
import com.smalko.weather.weather.util.Attributes;
import com.smalko.weather.weather.util.UrlPath;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static com.smalko.weather.weather.util.Attributes.*;

@WebServlet(name = "LoginServlet", value = UrlPath.LOGIN)
public class LoginServlet extends BaseServlet {
    private static final Duration ONE_DAY = Duration.ofDays(1);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);

        var errors = (String) request.getSession().getAttribute(ATTRIBUTE_ERROR);
        if (errors != null){
            putAttributeInModel("errors", errors);
            request.getSession().removeAttribute(ATTRIBUTE_ERROR);
        }

        var errorAndLocation = (String) request.getSession().getAttribute(ATTRIBUTE_ERROR_ADD_LOCATION);
        if (errorAndLocation != null){
            putAttributeInModel("errorAndLocation", errorAndLocation);
        }
        // TODO: 27.11.2023 Дописать в html
        super.processTemplate("login", request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);

        var username = request.getParameter("username");
        var password = request.getParameter("password");
        if (username != null && password != null) {
            username = username.trim();
            password = password.trim();
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
                return;
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
