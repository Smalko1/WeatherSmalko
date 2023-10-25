package com.smalko.weather.weather.repository;

import com.smalko.weather.weather.entity.Location;
import com.smalko.weather.weather.util.repository.RepositoryUtil;
import jakarta.persistence.EntityManager;

public class LocationRepository extends RepositoryUtil<Integer, Location> {
    private final EntityManager entityManager;
    public LocationRepository(Class<Location> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
        this.entityManager = entityManager;
    }
}
