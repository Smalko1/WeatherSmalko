package com.smalko.weather.weather.controller;

import com.smalko.weather.weather.util.HibernateUtil;
import com.smalko.weather.weather.util.SessionCleanupService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebListener
public class BaseListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    public BaseListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SessionCleanupService.startSessionCleanupTask();
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
        HibernateUtil.shutdown();
    }

}
