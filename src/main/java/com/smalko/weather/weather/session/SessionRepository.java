package com.smalko.weather.weather.session;

import com.smalko.weather.weather.util.repository.RepositoryUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SessionRepository extends RepositoryUtil<Integer, Session> {

    private static SessionRepository instance;
    private SessionRepository(Class<Session> clazz, EntityManager entityManager) {
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
                .orderBy(criteriaBuilder.asc(from.get("expiresAt")));

        var resultList = getEntityManager().createQuery(criteria)
                .getResultList();

        List<Session> result = new ArrayList<>();
        for (Session value : resultList) {
            if (value.getExpiresAt().isBefore(now)) {
                result.add(value);
            } else
                break;
        }

        return result;
    }
}
