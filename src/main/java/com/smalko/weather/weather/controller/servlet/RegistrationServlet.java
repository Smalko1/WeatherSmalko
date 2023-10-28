package com.smalko.weather.weather.controller.servlet;

import com.smalko.weather.weather.util.PathHelper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegistrationServlet", value = "/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher(PathHelper.gatPath("test")).include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var username = request.getParameter("username");
        var password = request.getParameter("password");

        System.out.println(username);
        System.out.println(password);
    }
}
