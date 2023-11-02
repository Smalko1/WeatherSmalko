package com.smalko.weather.weather.user;

import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.user.mapper.UserMapper;
import com.smalko.weather.weather.user.validator.CreateUsersValidator;
import com.smalko.weather.weather.user.validator.Error;
import com.smalko.weather.weather.util.HibernateUtil;
import com.smalko.weather.weather.util.PasswordHashing;
import jakarta.persistence.EntityManager;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

public class UsersService {
    private static final UsersService INSTANCE = new UsersService();
    private static final CreateUsersValidator validator = CreateUsersValidator.getInstance();
    private static final Logger log = LoggerFactory.getLogger(UsersService.class);

    public ResultRegistrationUser registrationUser(CreateUsersDto users) {
        ResultRegistrationUser result;

        var validationResult = validator.isValid(users);
        if (validationResult.isValid()) {
            log.info("user is valid");
            //HashPassword
            users.setPassword(PasswordHashing.hashPassword(users.getPassword()));

            log.info("user has new hash password");
            //map
            var usersEntity = UserMapper.INSTANCE.userToUserEntity(users);
            log.info("mapping user is userEntity");

            var entityManager = (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                    (proxy, method, args1) -> method.invoke(HibernateUtil.getSessionFactory().getCurrentSession(), args1));
            log.info("Create entityManager");
            entityManager.getTransaction().begin();
            //usersRepository.save
            try {
                UsersRepository.getInstance(entityManager).save(usersEntity);
                log.info("Registration new users is successful");
                result = ResultRegistrationUser.builder().
                        errors(List.of()).build();
                entityManager.getTransaction().commit();
            } catch (HibernateException | UndeclaredThrowableException e) {
                log.error("The name has already been taken by another user", e);
                result = ResultRegistrationUser.builder()
                        .errors(List.of(Error.of("ConstraintViolationException", "The name has already been taken by another user")))
                        .build();
                entityManager.getTransaction().rollback();
            }
        } else {
            log.error("user is not valid");
            result = ResultRegistrationUser.builder()
                    .errors(validationResult.getErrors())
                    .build();
        }
        return result;
    }


    public static UsersService getInstance() {
        return INSTANCE;
    }
}
