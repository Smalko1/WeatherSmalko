package com.smalko.weather.weather.util;

import com.smalko.weather.weather.session.SessionService;
import lombok.experimental.UtilityClass;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class SessionCleanupService {
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public static void startSessionCleanupTask() {
        // Запустить задачу каждый час для очистки устаревших сессий
        scheduler.scheduleAtFixedRate(SessionCleanupService::cleanupExpiredSessions, 0, 1, TimeUnit.HOURS);
    }

    private static void cleanupExpiredSessions() {
        // Ваш код для удаления устаревших сессий из базы данных
        SessionService.getInstance().takingAndRemoveSessions();
    }
}
