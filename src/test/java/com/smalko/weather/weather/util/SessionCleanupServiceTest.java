package com.smalko.weather.weather.util;

import com.smalko.weather.weather.session.Session;
import com.smalko.weather.weather.session.SessionRepository;
import com.smalko.weather.weather.session.SessionService;
import com.smalko.weather.weather.user.UsersEntity;
import com.smalko.weather.weather.user.UsersRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class SessionCleanupServiceTest {

    @Mock
    private SessionService sessionService = SessionService.getInstance();


    @Test
    public void testCleanupExpiredSessions() {
        // Создаем несколько сессий, одна из которых устарела

    }

}