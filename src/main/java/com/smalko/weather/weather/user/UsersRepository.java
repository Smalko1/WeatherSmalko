package com.smalko.weather.weather.user;

import com.smalko.weather.weather.util.repository.RepositoryUtil;
import jakarta.persistence.EntityManager;

public class UsersRepository extends RepositoryUtil<Integer, UsersEntity> {
    private static UsersRepository instance;
    public UsersRepository(Class<UsersEntity> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }

    public static UsersRepository getInstance(EntityManager entityManager){
        if (instance == null){
            instance = new UsersRepository(UsersEntity.class, entityManager);
        }
        return instance;
    }
}
