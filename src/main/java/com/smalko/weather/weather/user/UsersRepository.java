package com.smalko.weather.weather.user;

import com.smalko.weather.weather.util.repository.RepositoryUtil;
import jakarta.persistence.EntityManager;

public class UsersRepository extends RepositoryUtil<Integer, UsersRepository> {
    private final EntityManager entityManager;
    public UsersRepository(Class<UsersRepository> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
        this.entityManager = entityManager;
    }
}
