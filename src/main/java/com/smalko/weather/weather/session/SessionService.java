package com.smalko.weather.weather.session;

import com.smalko.weather.weather.user.dto.ReadUserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionService {
    private static final SessionService INSTANCE = new SessionService();

    public void createSession(ReadUserDto readUserDto){

    }

    public static SessionService getInstance() {
        return INSTANCE;
    }
}
