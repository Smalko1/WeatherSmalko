package com.smalko.weather.weather.controller.servlet;

import com.smalko.weather.weather.user.UsersService;
import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.util.PathHelper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RegistrationServlet", value = "/registration")
public class RegistrationServlet extends BaseServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher(PathHelper.gatPath("registration")).include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        var username = request.getParameter("username");
        var password = request.getParameter("password");

        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            var createUser = CreateUsersDto.builder()
                    .name(username)
                    .password(password)
                    .build();
            var user = UsersService.getInstance().registrationUser(createUser);
            if (user.hasErrors()){
                request.setAttribute("errors", user.getErrors());
                doGet(request, response);
                return;
            }
            response.sendRedirect( "/home");
        }
    }
}
