package com.smalko.weather.weather.controller.servlet;

import com.smalko.weather.weather.location.service.OpenWeatherAPI;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.smalko.weather.weather.util.Attributes.ATTRIBUTE_SEARCH_CITY;
import static com.smalko.weather.weather.util.Attributes.ATTRIBUTE_USER_ID;
import static com.smalko.weather.weather.util.UrlPath.HOME;

@WebServlet
public class BaseServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(BaseServlet.class);
    private Map<String, Object> model;
    private TemplateEngine templateEngine;

    @Override
    public void init() {

        ServletContext servletContext = this.getServletContext();
        WebApplicationTemplateResolver templateResolver =
                new WebApplicationTemplateResolver(JakartaServletWebApplication.buildApplication(servletContext));
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        //templateResolver.setCacheTTLMs(3600000L);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        this.templateEngine = templateEngine;


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        model = new HashMap<>();
        try {
            setAttributeForHeader(request);
            request.setAttribute("model", model);
        } catch (RuntimeException e) {
            log.error("Error when saving attributes");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var search = request.getParameter("search");

        if (search != null) {
            search = search.trim();
            if (!search.isEmpty()) {
                log.info("search weather by {}", search);
                var searchCity = OpenWeatherAPI.requestCoordinatesByCity(search);

                request.getSession().setAttribute(ATTRIBUTE_SEARCH_CITY, searchCity);
                response.sendRedirect(request.getContextPath() + HOME);
                return;
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
        if (request.getSession().getAttribute(ATTRIBUTE_USER_ID) == null) {
            model.put("loggedIn", false);
        } else
            model.put("loggedIn", true);
    }

    protected void putAttributeInModel(String key, Object value) {
        model.put(key, value);
    }

    protected void removeSessionAttribute(HttpServletRequest request, String... attributes) {
        var session = request.getSession();
        for (String attribute : attributes) {
            if (attribute != null) {
                session.removeAttribute(attribute);
            }
        }
    }

    protected void redirect(String redirect, HttpServletRequest request ,HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + redirect);
    }
}
