package com.smalko.weather.weather.util;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private static SessionFactory createSessionFactory() {
        try {
            var configuration = new Configuration();

            configuration.configure();


            return sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {

            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }


    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            sessionFactory = createSessionFactory();
        }
        return sessionFactory;

    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
