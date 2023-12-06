package com.smalko.weather.weather.controller.servlet;

import com.smalko.weather.weather.session.SessionService;
import com.smalko.weather.weather.user.UsersService;
import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.user.result.LoginResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.Duration;

import static com.smalko.weather.weather.util.Attributes.ATTRIBUTE_ERROR;
import static com.smalko.weather.weather.util.Attributes.ATTRIBUTE_ERROR_ADD_LOCATION;
import static com.smalko.weather.weather.util.Attributes.ATTRIBUTE_USER_ID;
import static com.smalko.weather.weather.util.UrlPath.HOME;
import static com.smalko.weather.weather.util.UrlPath.LOGIN;

@WebServlet(name = "LoginServlet", value = LOGIN)
public class LoginServlet extends BaseServlet {
    private static final Duration ONE_DAY = Duration.ofDays(1);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);

        var errors = request.getSession().getAttribute(ATTRIBUTE_ERROR);
        if (errors != null){
            putAttributeInModel("errors", errors);
        }

        var errorAndLocation = (String) request.getSession().getAttribute(ATTRIBUTE_ERROR_ADD_LOCATION);
        if (errorAndLocation != null){
            putAttributeInModel("errorAndLocation", errorAndLocation);
        }

        removeSessionAttribute(request, ATTRIBUTE_ERROR, ATTRIBUTE_ERROR_ADD_LOCATION);
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
                response.sendRedirect(request.getContextPath() + HOME);
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
