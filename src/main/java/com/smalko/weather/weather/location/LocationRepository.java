package com.smalko.weather.weather.location;

import com.smalko.weather.weather.user.UsersEntity;
import com.smalko.weather.weather.util.repository.RepositoryUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.List;

public class LocationRepository extends RepositoryUtil<Integer, LocationEntity> {
    private static LocationRepository instance;
    private LocationRepository(Class<LocationEntity> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }

    public static LocationRepository getInstance(EntityManager entityManager){
        if (instance == null){
            instance = new LocationRepository(LocationEntity.class, entityManager);
        }
        return instance;
    }

    public List<LocationEntity> getLocationsByUserId(Integer userId) {
        var criteriaBuilder = getEntityManager().getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(LocationEntity.class);

        var from = criteria.from(UsersEntity.class);
        Join<UsersEntity, LocationEntity> join= from.join("locations", JoinType.INNER);

        criteria.select(join)
                .where(criteriaBuilder.equal(from.get("id"), userId));

        return getEntityManager().createQuery(criteria).getResultList();
    }

}
