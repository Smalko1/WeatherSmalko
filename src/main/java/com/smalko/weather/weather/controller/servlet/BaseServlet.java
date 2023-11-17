package com.smalko.weather.weather.controller.servlet;

import com.smalko.weather.weather.location.HttpStatus;
import com.smalko.weather.weather.location.service.OpenWeatherAPI;
import com.smalko.weather.weather.util.UrlPath;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.smalko.weather.weather.util.UrlPath.HOME;

@WebServlet
public class BaseServlet extends HttpServlet {
    private Map<String, Object> model;
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
        model = new HashMap<>();
        setAttributeForHeader(request);
        request.setAttribute("model", model);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var search = request.getParameter("search");

        if (search != null && !search.isEmpty()) {
            var searchCityResult = OpenWeatherAPI.requestWeatherByCity(search);
            if (searchCityResult.getStatus().equals(HttpStatus.HTTP_OK)) {
                request.getSession().setAttribute("searchCityResult", searchCityResult);
                response.sendRedirect(request.getContextPath() + HOME);
            }else{
                putAttributeInModel("UnsuccessfulSearch", "Unsuccessful search");
            }
        }
    }

    protected void processTemplate(String templateName, HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        IWebExchange iWebExchange = JakartaServletWebApplication.buildApplication(this.getServletContext()).buildExchange(request, response);
        WebContext webContext = new WebContext(iWebExchange);

        templateEngine.process(templateName, webContext, response.getWriter());
    }

    private void setAttributeForHeader(HttpServletRequest request) {
        if (request.getSession().getAttribute("userId") == null) {
            model.put("loggedIn", false);
        } else
            model.put("loggedIn", true);
    }

    protected void putAttributeInModel(String key, Object value) {
        model.put(key, value);
    }
}
