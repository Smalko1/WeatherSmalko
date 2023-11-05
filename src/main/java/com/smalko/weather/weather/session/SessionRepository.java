package com.smalko.weather.weather.session;

import com.smalko.weather.weather.util.repository.RepositoryUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class SessionRepository extends RepositoryUtil<UUID, Session> {

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

    public List<Session> findAllExpiredSession() {
        LocalDateTime now = LocalDateTime.now();
        var criteriaBuilder = getEntityManager().getCriteriaBuilder();
        var criteria = criteriaBuilder.createQuery(Session.class);
        var from = criteria.from(Session.class);

        criteria.select(from)
                .where(criteriaBuilder.lessThan(from.get("expiresat"), now));

        var query = getEntityManager().createQuery(criteria);
        return query.getResultList();
    }
}
