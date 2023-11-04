package com.smalko.weather.weather.session;

import com.smalko.weather.weather.session.dto.CreateSessionDto;
import com.smalko.weather.weather.session.dto.ReadSessionDto;
import com.smalko.weather.weather.session.mapper.SessionMapper;
import com.smalko.weather.weather.user.dto.ReadUserDto;
import com.smalko.weather.weather.user.mapper.UserMapper;
import com.smalko.weather.weather.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionService {
    private static final SessionService INSTANCE = new SessionService();
    private static final SessionMapper mapper = SessionMapper.INSTANCE;

    public ReadSessionDto createSession(ReadUserDto readUserDto){

        var createSessionDto = new CreateSessionDto(UserMapper.INSTANCE.readUserDtoToUserEntity(readUserDto));

        var session = mapper.createSessionDtoToSessionEntity(createSessionDto);

        var entityManager = (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                (proxy, method, args1) -> method.invoke(HibernateUtil.getSessionFactory().getCurrentSession(), args1));

        entityManager.getTransaction();

        var newSession = SessionRepository.getInstance(entityManager).save(session);

        entityManager.getTransaction().commit();

        return mapper.sessionEntityToReadSessionDto(newSession);
    }

    public static SessionService getInstance() {
        return INSTANCE;
    }
}
