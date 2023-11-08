package com.smalko.weather.weather.controller.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet
public class BaseServlet extends HttpServlet {
    private static final Map<String, Object> MODEL = new HashMap<>();
    private TemplateEngine templateEngine;
    @Override
    public void init() {

        ServletContext servletContext = this.getServletContext();
        WebApplicationTemplateResolver templateResolver =
                new WebApplicationTemplateResolver(JakartaServletWebApplication.buildApplication(servletContext));
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        //templateResolver.setCacheTTLMs(3600000L);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        this.templateEngine = templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setAttributeForHeader(request);
        request.setAttribute("model", MODEL);
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

    protected void processTemplate(String templateName, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        IWebExchange iWebExchange = JakartaServletWebApplication.buildApplication(this.getServletContext()).buildExchange(request, response);
        WebContext webContext = new WebContext(iWebExchange);

        templateEngine.process(templateName, webContext, response.getWriter());
    }
    private void setAttributeForHeader(HttpServletRequest request) {
        if (request.getSession().getAttribute("userId") == null){
            MODEL.put("loggedIn", false);
        }else
            MODEL.put("loggedIn", true);
    }

    protected void putModel(String key, Object value){
        MODEL.put(key, value);
    }
}
