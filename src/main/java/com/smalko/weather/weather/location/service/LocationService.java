package com.smalko.weather.weather.location.service;

import com.smalko.weather.weather.location.Location;
import com.smalko.weather.weather.location.LocationRepository;
import com.smalko.weather.weather.location.dto.CreateLocationDto;
import com.smalko.weather.weather.location.mapper.LocationMapper;
import com.smalko.weather.weather.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;


public class LocationService {
    private static final LocationService INSTANCE = new LocationService();
    private static final Logger log = LoggerFactory.getLogger(LocationService.class);


    public void saveLocation(CreateLocationDto locationDto) {
        var locationEntity = LocationMapper.INSTANCE.createLocationDtoToLocationEntity(locationDto);

        var entityManager = (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                (proxy, method, args1) -> method.invoke(HibernateUtil.getSessionFactory().getCurrentSession(), args1));
        log.info("Create entityManager");
        entityManager.getTransaction().begin();

        try {
            LocationRepository.getInstance(entityManager).save(locationEntity);
            log.info("Save location is successful");
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            entityManager.getTransaction().rollback();
        }
    }

    public static LocationService getInstance() {
        return INSTANCE;
    }
}
