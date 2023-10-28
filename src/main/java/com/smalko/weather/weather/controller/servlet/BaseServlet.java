package com.smalko.weather.weather.controller.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet
public class BaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var search = request.getParameter("search");

        if (search != null && !search.isEmpty()) {
            // Выполняем поиск, так как параметр "searchQuery" есть и не пуст
            // Ваша логика по поиску...
            // Отправляэм результат
            System.out.println("Hello search");
            System.out.println(search);
        }
    }
}
