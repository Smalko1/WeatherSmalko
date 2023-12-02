package com.smalko.weather.weather.controller.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.smalko.weather.weather.util.Attributes.ATTRIBUTE_ERROR;
import static com.smalko.weather.weather.util.UrlPath.ERROR_HANDLER;
import static com.smalko.weather.weather.util.UrlPath.HOME;

@WebServlet(name = "errorHandel", value = ERROR_HANDLER)
public class ErrorHandlerServlet extends BaseServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        request.getSession().setAttribute(ATTRIBUTE_ERROR, "Sorry, something went wrong.");
        response.sendRedirect(request.getContextPath() + HOME);
    }
}
