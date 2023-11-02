package com.smalko.weather.weather.util;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class HibernateUtilTest {

    private SessionFactory sessionFactory;

    @BeforeEach
    void setUp() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Test
    void testSessionFactoryCreation() {
        assertThat(sessionFactory).isNotNull();
    }

    @AfterEach
    void tearDown() {
        HibernateUtil.shutdown();
    }
}