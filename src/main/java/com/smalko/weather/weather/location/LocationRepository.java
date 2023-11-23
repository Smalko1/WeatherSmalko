package com.smalko.weather.weather.location;

import com.smalko.weather.weather.util.repository.RepositoryUtil;
import jakarta.persistence.EntityManager;

public class LocationRepository extends RepositoryUtil<Integer, Location> {
    private static LocationRepository instance;
    private LocationRepository(Class<Location> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }

    public static LocationRepository getInstance(EntityManager entityManager){
        if (instance == null){
            instance = new LocationRepository(Location.class, entityManager);
        }
        return instance;
    }
}
