package com.smalko.weather.weather.controller.servlet;

import com.smalko.weather.weather.util.Attributes;
import com.smalko.weather.weather.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.smalko.weather.weather.util.Attributes.ATTRIBUTE_ERROR;
import static com.smalko.weather.weather.util.UrlPath.APP_EXCEPTION_HANDLER;
import static com.smalko.weather.weather.util.UrlPath.HOME;

@WebServlet(APP_EXCEPTION_HANDLER)
public class AppExceptionHandler extends BaseServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message = "Sorry, there's been some kind of error.";
        request.getSession().setAttribute(ATTRIBUTE_ERROR, message);
        response.sendRedirect(request.getContextPath() + HOME);
    }
}
