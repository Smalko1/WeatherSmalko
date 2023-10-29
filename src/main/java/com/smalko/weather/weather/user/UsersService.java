package com.smalko.weather.weather.user;

import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.user.mapper.CreateUserMapper;
import com.smalko.weather.weather.user.mapper.ReadUserMapper;
import com.smalko.weather.weather.user.validator.CreateUsersValidator;
import com.smalko.weather.weather.user.validator.Error;
import com.smalko.weather.weather.user.validator.ValidationResult;
import com.smalko.weather.weather.util.HibernateUtil;
import com.smalko.weather.weather.util.PasswordHashing;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import java.lang.reflect.Proxy;
import java.util.List;

public class UsersService {
    private static final UsersService INSTANCE = new UsersService();
    private static final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private static final ReadUserMapper readUserMapper = ReadUserMapper.getInstance();
    private static final CreateUsersValidator validator = CreateUsersValidator.getInstance();

    public ResultRegistrationUser createUser(CreateUsersDto users) {
        ResultRegistrationUser result;

        var validationResult = validator.isValid(users);
        if (validationResult.isValid()) {
            //HashPassword
            users.setPassword(PasswordHashing.hashPassword(users.getPassword()));

            //map
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            var usersEntity = createUserMapper.mapFrom(users);
            var entityManager = (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
            entityManager.getTransaction().begin();
            //usersRepository.save
            try {
                UsersRepository.getInstance(entityManager).save(usersEntity);
                result = ResultRegistrationUser.builder().build();
            } catch (ConstraintViolationException e) {
                result = ResultRegistrationUser.builder()
                        .errors(List.of(Error.of("ConstraintViolationException", "The name has already been taken by another user")))
                        .build();
            } finally {
                entityManager.getTransaction().commit();
            }
        } else {
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
