package com.smalko.weather.weather.controller.servlet;

import com.smalko.weather.weather.session.Session;
import com.smalko.weather.weather.session.SessionService;
import com.smalko.weather.weather.user.UsersService;
import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.util.PathHelper;
import com.smalko.weather.weather.util.UrlPath;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = UrlPath.LOGIN)
public class LoginServlet extends BaseServlet {
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

        var result = UsersService.getInstance().authenticationUser(userLogin);
        if (result.isSuccess()){
            var session = request.getSession();
            session.setAttribute("user", result.getUser());
        }else {
            request.setAttribute("errors", result.getErrors());
            doGet(request, response);
        }
    }
}
