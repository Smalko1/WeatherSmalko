package com.smalko.weather.weather.user;

import com.smalko.weather.weather.location.Location;
import com.smalko.weather.weather.util.repository.RepositoryUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class UsersRepository extends RepositoryUtil<Integer, UsersEntity> {
    private static UsersRepository instance;
    private UsersRepository(Class<UsersEntity> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }

    public static UsersRepository getInstance(EntityManager entityManager){
        if (instance == null){
            instance = new UsersRepository(UsersEntity.class, entityManager);
        }
        return instance;
    }

    public UsersEntity findByName(String username) {
        var criteriaBuilder = getEntityManager().getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(UsersEntity.class);
        var from = criteria.from(UsersEntity.class);

        criteria.select(from)
                .where(criteriaBuilder.equal(from.get("username"), username));

        return getEntityManager().createQuery(criteria).getSingleResult();
    }
}
