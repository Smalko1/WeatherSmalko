package com.smalko.weather.weather.controller.servlet;

import com.smalko.weather.weather.user.UsersService;
import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.user.validator.Error;
import com.smalko.weather.weather.util.UrlPath;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "RegistrationServlet", value = UrlPath.REGISTRATION)
public class RegistrationServlet extends BaseServlet {
    private static final String ATTRIBUTE_ERRORS = "errors";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        super.doGet(request, response);

        var errors = request.getSession().getAttribute(ATTRIBUTE_ERRORS);
        if (errors != null) {
            putAttributeInModel("errors", errors);
            request.getSession().removeAttribute(ATTRIBUTE_ERRORS);
        }
        super.processTemplate("registration", request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);

        var username = request.getParameter("username");
        var password = request.getParameter("password");
        if (username != null && password != null) {
            var createUser = CreateUsersDto.builder()
                    .name(username)
                    .password(password)
                    .build();
            var result = UsersService.getInstance().registrationUser(createUser);
            if (!result.isSuccess()) {
                request.getSession().setAttribute(ATTRIBUTE_ERRORS, result.getErrors());
            } else {
                response.sendRedirect(UrlPath.LOGIN);
                return;
            }
        }
        doGet(request, response);
    }
}
