package com.smalko.weather.weather.session;

import com.smalko.weather.weather.session.dto.CreateSessionDto;
import com.smalko.weather.weather.session.mapper.SessionMapper;
import com.smalko.weather.weather.session.result.GetSessionResult;
import com.smalko.weather.weather.session.result.SaveSessionResult;
import com.smalko.weather.weather.user.dto.ReadUserDto;
import com.smalko.weather.weather.user.mapper.UserMapper;
import com.smalko.weather.weather.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.NoSuchElementException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionService {
    private static final SessionService INSTANCE = new SessionService();
    private static final SessionMapper mapper = SessionMapper.INSTANCE;
    private static final Logger log = LoggerFactory.getLogger(SessionService.class);

    public SaveSessionResult saveSession(ReadUserDto readUserDto){

        var createSessionDto = new CreateSessionDto(UserMapper.INSTANCE.readUserDtoToUserEntity(readUserDto));
        log.info("Create {} and mappers readUserDto to UserEntity", createSessionDto);

        var session = mapper.createSessionDtoToSessionEntity(createSessionDto);
        log.info("Mapping {}", session);

        var entityManager = (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                (proxy, method, args1) -> method.invoke(HibernateUtil.getSessionFactory().getCurrentSession(), args1));

        entityManager.getTransaction().begin();
        try {
            var newSession = SessionRepository.getInstance(entityManager).save(session);
            entityManager.getTransaction().commit();
            log.info("Save session successful");
            return SaveSessionResult.resultSuccessful(mapper.sessionEntityToReadSessionDto(newSession));
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            log.error("Create error", e);
            return SaveSessionResult.resultUnSuccessful();
        }
    }

    public GetSessionResult getSessionById(Integer sessionId) {
        var entityManager = (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                (proxy, method, args1) -> method.invoke(HibernateUtil.getSessionFactory().getCurrentSession(), args1));
        entityManager.getTransaction().begin();
        try {
            var session = SessionRepository.getInstance(entityManager)
                    .findById(sessionId)
                    .orElseThrow(NoSuchElementException::new);
            entityManager.getTransaction().commit();
            log.info("Retrieving a session by its id");
            return GetSessionResult.result(UserMapper.INSTANCE.userToUserDto(session.getUsersEntity()));

        }catch (NoResultException | HibernateException | NoSuchElementException e){
            entityManager.getTransaction().rollback();
            log.error("An error occurred while taking a session", e);
            return GetSessionResult.result();
        }
    }

    public void takingAndRemoveSessions() {
        var entityManager = (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                (proxy, method, args1) -> method.invoke(HibernateUtil.getSessionFactory().getCurrentSession(), args1));

        entityManager.getTransaction().begin();
        try {
            var expiredSessionSession = SessionRepository.getInstance(entityManager).findAllExpiredSession();
            log.info("Get expired session, {}", expiredSessionSession);
            entityManager.getTransaction().commit();
            for (SessionEntity session : expiredSessionSession) {
                entityManager.getTransaction().begin();
                SessionRepository.getInstance(entityManager).deleteEntity(session);
                entityManager.getTransaction().commit();
            }
            entityManager.getTransaction().commit();
        }catch (NoResultException | TransactionException e){
            log.error("If exception = NoResultException expired sessions," +
                      " if exception = TransactionException Indicates that a transaction could not be begun, committed or rolled back.",e);
            entityManager.getTransaction().rollback();
        }
    }

    public static SessionService getInstance() {
        return INSTANCE;
    }
}
