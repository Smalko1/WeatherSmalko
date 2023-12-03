package com.smalko.weather.weather.session;

import com.smalko.weather.weather.util.repository.RepositoryUtil;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SessionRepository extends RepositoryUtil<Integer, SessionEntity> {

    private static SessionRepository instance;
    private SessionRepository(Class<SessionEntity> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }

    public static SessionRepository getInstance(EntityManager entityManager){
        if (instance == null){
            instance = new SessionRepository(SessionEntity.class, entityManager);
        }
        return instance;
    }

    public List<SessionEntity> findAllExpiredSession() {
        LocalDateTime now = LocalDateTime.now();
        var criteriaBuilder = getEntityManager().getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(SessionEntity.class);
        var from = criteria.from(SessionEntity.class);

        criteria.select(from)
                .orderBy(criteriaBuilder.asc(from.get("expiresAt")));

        var resultList = getEntityManager().createQuery(criteria)
                .getResultList();

        List<SessionEntity> result = new ArrayList<>();
        for (SessionEntity value : resultList) {
            if (value.getExpiresAt().isBefore(now)) {
                result.add(value);
            } else
                break;
        }

        return result;
    }
}
