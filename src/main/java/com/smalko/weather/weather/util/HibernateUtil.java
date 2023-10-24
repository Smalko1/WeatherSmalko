package com.smalko.weather.weather.util;

import com.smalko.weather.weather.entity.Session;
import com.smalko.weather.weather.entity.Users;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.Location;

@UtilityClass
public class HibernateUtil {
    private static final Logger log = LoggerFactory.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = createSessionFactory();
        }
        log.info("Get sessionFactory");
        return sessionFactory;
    }


    private static SessionFactory createSessionFactory() {
        try {
            var configuration = new Configuration();

            configuration.configure();

            configuration.addAnnotatedClass(Location.class);
            configuration.addAnnotatedClass(Session.class);
            configuration.addAnnotatedClass(Users.class);

            log.info("Create sessionFactory");
            return sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            log.error("Initial SessionFactory creation failed:", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}
