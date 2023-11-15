package com.smalko.weather.weather.controller.servlet;

import com.smalko.weather.weather.user.UsersService;
import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.util.UrlPath;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RegistrationServlet", value = UrlPath.REGISTRATION)
public class RegistrationServlet extends BaseServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        super.doGet(request, response);
        super.processTemplate("registration", request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);

        var createUser = CreateUsersDto.builder()
                .name(request.getParameter("username"))
                .password(request.getParameter("password"))
                .build();
        var result = UsersService.getInstance().registrationUser(createUser);
        if (!result.isSuccess()) {
            putAttributeInModel("errors", result.getErrors());
            super.processTemplate("registration", request, response);
        }
        response.sendRedirect(UrlPath.LOGIN);
    }
}
