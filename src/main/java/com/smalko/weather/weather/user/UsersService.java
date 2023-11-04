package com.smalko.weather.weather.user;

import com.smalko.weather.weather.user.dto.CreateUsersDto;
import com.smalko.weather.weather.user.dto.ReadUserDto;
import com.smalko.weather.weather.user.mapper.UserMapper;
import com.smalko.weather.weather.user.result.LoginResult;
import com.smalko.weather.weather.user.result.RegistrationResult;
import com.smalko.weather.weather.user.validator.CreateUsersValidator;
import com.smalko.weather.weather.user.validator.Error;
import com.smalko.weather.weather.util.HibernateUtil;
import com.smalko.weather.weather.util.PasswordHashing;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UsersService {
    private static final UsersService INSTANCE = new UsersService();
    private static final CreateUsersValidator validator = CreateUsersValidator.getInstance();
    private static final Logger log = LoggerFactory.getLogger(UsersService.class);

    public RegistrationResult registrationUser(CreateUsersDto users) {
        List<Error> errors = new ArrayList<>();

        var validationResult = validator.isValid(users);
        if (validationResult.isValid()) {
            log.info("user is valid");
            //HashPassword
            users.setPassword(PasswordHashing.hashPassword(users.getPassword()));

            log.info("user has new hash password");
            //map
            var usersEntity = UserMapper.INSTANCE.userCreateDtoToUserEntity(users);
            log.info("mapping user is userEntity");

            var entityManager = (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                    (proxy, method, args1) -> method.invoke(HibernateUtil.getSessionFactory().getCurrentSession(), args1));
            log.info("Create entityManager");
            entityManager.getTransaction().begin();
            //usersRepository.save
            try {
                UsersRepository.getInstance(entityManager).save(usersEntity);
                log.info("Registration new users is successful");
                entityManager.getTransaction().commit();
            } catch (HibernateException | UndeclaredThrowableException e) {
                log.error("The name has already been taken by another user", e);
                errors.add(Error.of("ConstraintViolationException", "The name has already been taken by another user"));

                entityManager.getTransaction().rollback();
            }
        } else {
            log.error("user is not valid");
            errors = validationResult.getErrors();


        }
        return RegistrationResult.result(errors);
    }

    public LoginResult authenticationUser(CreateUsersDto users) {
        List<Error> errors = new ArrayList<>();
        ReadUserDto readUserDto = null;

        var validationResult = validator.isValid(users);
        if (validationResult.isValid()) {
            log.info("user is valid");

            //map
            var usersEntity = UserMapper.INSTANCE.userCreateDtoToUserEntity(users);
            log.info("mapping user is userEntity");

            var entityManager = (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                    (proxy, method, args1) -> method.invoke(HibernateUtil.getSessionFactory().getCurrentSession(), args1));
            log.info("Create entityManager");
            entityManager.getTransaction().begin();
            //usersEntity отправляю имя для проверки совпадения в базе данных
            try {
                var maybeUser = UsersRepository.getInstance(entityManager).findByName(usersEntity.getUsername());
                log.info("The takeaway {} matches the name", maybeUser);
                boolean pass = PasswordHashing.checkPassword(usersEntity.getPassword(), maybeUser.getPassword());
                log.info("{} - if true, the user is authenticated, if false, the user failed password verification", pass);
                entityManager.getTransaction().commit();

                if (pass){
                    readUserDto = UserMapper.INSTANCE.userToUserDto(maybeUser);
                }else
                    errors.add(Error.of("IncorrectPassword", "Incorrect password"));

            }catch (NoResultException e){
                log.error("Incorrect login or password", e);
                errors.add(Error.of("IncorrectAuthentication", "Incorrect login or password"));
                entityManager.getTransaction().commit();
            }
        } else {
            log.error("user is not valid");
            errors = validationResult.getErrors();
        }
        return LoginResult.result(readUserDto, errors);
    }


    public static UsersService getInstance() {
        return INSTANCE;
    }
}
