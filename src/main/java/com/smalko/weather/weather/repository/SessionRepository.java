package com.smalko.weather.weather.repository;

import com.smalko.weather.weather.entity.Session;
import com.smalko.weather.weather.util.repository.RepositoryUtil;
import jakarta.persistence.EntityManager;

public class SessionRepository extends RepositoryUtil<Integer, Session> {
    private final EntityManager entityManager;
    public SessionRepository(Class<Session> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
        this.entityManager = entityManager;
    }
}
