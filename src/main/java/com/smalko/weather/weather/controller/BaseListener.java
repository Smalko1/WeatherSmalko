package com.smalko.weather.weather.controller;

import com.smalko.weather.weather.util.HibernateUtil;
import com.smalko.weather.weather.util.SessionCleanupService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class BaseListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    public BaseListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SessionCleanupService.startSessionCleanupTask();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateUtil.shutdown();
    }

}
