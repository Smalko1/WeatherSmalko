package com.smalko.weather.weather.location.service;

import com.smalko.weather.weather.location.Location;
import com.smalko.weather.weather.location.LocationRepository;
import com.smalko.weather.weather.location.dto.CreateLocationDto;
import com.smalko.weather.weather.location.mapper.LocationMapper;
import com.smalko.weather.weather.location.result.FavoriteLocationsUserResult;
import com.smalko.weather.weather.user.UsersEntity;
import com.smalko.weather.weather.user.UsersRepository;
import com.smalko.weather.weather.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.NoSuchParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.NoSuchElementException;


public class LocationService {
    private static final LocationService INSTANCE = new LocationService();
    private static final Logger log = LoggerFactory.getLogger(LocationService.class);


    public void saveLocationInUser(CreateLocationDto locationDto) {
        var locationEntity = LocationMapper.INSTANCE.createLocationDtoToLocationEntity(locationDto);

        var entityManager = (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                (proxy, method, args1) -> method.invoke(HibernateUtil.getSessionFactory().getCurrentSession(), args1));
        log.info("Create entityManager");
        entityManager.getTransaction().begin();

        try {
            var Users = UsersRepository.getInstance(entityManager)
                    .findById(locationDto.getUserId())
                    .orElseThrow(() -> new NoSuchElementException("The user is not found by the specified identifier: " + locationEntity.getId()));

            Users.addLocation(locationEntity);

            log.info("Save location is successful");
            entityManager.getTransaction().commit();
        } catch (HibernateException | NoSuchElementException e) {
            log.error(e.getMessage());
            entityManager.getTransaction().rollback();
        }
    }

    public FavoriteLocationsUserResult findAllFavoriteUserLocations(Integer userId) {
        FavoriteLocationsUserResult favoriteLocations = new FavoriteLocationsUserResult();
        var entityManager = (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                (proxy, method, args1) -> method.invoke(HibernateUtil.getSessionFactory().getCurrentSession(), args1));
        log.info("Create entityManager");
        entityManager.getTransaction().begin();
        try {
            var locationsByUserId = LocationRepository.getInstance(entityManager).getLocationsByUserId(userId);
            entityManager.getTransaction().commit();
            for (Location location : locationsByUserId) {
                favoriteLocations.addWeatherResult(
                        location.getId(), OpenWeatherAPI.requestWeather(location.getLatitude(), location.getLongitude(), location.getName()));
            }
        }catch (NullPointerException| HibernateException e){
            entityManager.getTransaction().rollback();
            favoriteLocations.setSuccessful(false);
        }
        return favoriteLocations;
    }

    public boolean removeLocationInUser(Integer userId, Integer locationId){
        var entityManager = (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                (proxy, method, args1) -> method.invoke(HibernateUtil.getSessionFactory().getCurrentSession(), args1));
        log.info("Create entityManager");
        entityManager.getTransaction().begin();
        try {
            var deleteSuccessful = LocationRepository.getInstance(entityManager).deleteLocation(userId, locationId);

            entityManager.getTransaction().commit();
            return deleteSuccessful;
        }catch (EntityNotFoundException | OptimisticLockException e){
            log.error("Error while removing location", e);
            entityManager.getTransaction().rollback();
            return false;
        }
    }

    public static LocationService getInstance() {
        return INSTANCE;
    }
}
