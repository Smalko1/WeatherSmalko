package com.smalko.weather.weather.session;

import com.smalko.weather.weather.user.UsersEntity;
import com.smalko.weather.weather.user.UsersRepository;
import com.smalko.weather.weather.util.repository.RepositoryUtil;
import jakarta.persistence.EntityManager;

public class SessionRepository extends RepositoryUtil<Integer, Session> {

    private static SessionRepository instance;
    public SessionRepository(Class<Session> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }

    public static SessionRepository getInstance(EntityManager entityManager){
        if (instance == null){
            instance = new SessionRepository(Session.class, entityManager);
        }
        return instance;
    }
}
