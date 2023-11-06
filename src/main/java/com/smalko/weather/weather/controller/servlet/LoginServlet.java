package com.smalko.weather.weather.controller.servlet;

import com.smalko.weather.weather.session.Session;
import com.smalko.weather.weather.session.SessionService;
import com.smalko.weather.weather.session.result.SaveSessionResult;
import com.smalko.weather.weather.user.UsersService;
import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.user.result.LoginResult;
import com.smalko.weather.weather.util.PathHelper;
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
        request.getRequestDispatcher(PathHelper.gatPath("login")).include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);

        var userLogin = CreateUsersDto.builder()
                .name(request.getParameter("username"))
                .password(request.getParameter("password"))
                .build();

        var loginResult = UsersService.getInstance().authenticationUser(userLogin);
        if (loginResult.isSuccess()){
            var session = request.getSession();
            session.setAttribute("user", loginResult.getUser().getName());
            createCookie(response, loginResult);
        }else {
            request.setAttribute("errors", loginResult.getErrors());
            doGet(request, response);
        }
    }

    private static void createCookie(HttpServletResponse response, LoginResult loginResult) {
        var sessionResult = SessionService.getInstance().saveSession(loginResult.getUser());
        var sessionCookie = new Cookie("sessionId", sessionResult.getReadSessionDto().getId().toString());
        sessionCookie.setMaxAge((int) ONE_DAY.toSeconds());
        response.addCookie(sessionCookie);
    }
}
