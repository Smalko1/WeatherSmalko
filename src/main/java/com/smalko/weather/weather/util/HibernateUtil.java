package com.smalko.weather.weather.util;

import com.smalko.weather.weather.location.LocationEntity;
import com.smalko.weather.weather.session.SessionEntity;
import com.smalko.weather.weather.user.UsersEntity;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static final Logger log = LoggerFactory.getLogger(HibernateUtil.class);

    private static SessionFactory createSessionFactory() {
        try {
            var configuration = new Configuration();

            configuration.configure();
            configuration.addAnnotatedClass(UsersEntity.class);
            configuration.addAnnotatedClass(SessionEntity.class);
            configuration.addAnnotatedClass(LocationEntity.class);

            log.info("Create SessionEntity Factory");

            var serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {

           log.error("Initial SessionFactory creation failed: ", ex);
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
        log.info("Shutdown session factory");
        getSessionFactory().close();
    }
}
