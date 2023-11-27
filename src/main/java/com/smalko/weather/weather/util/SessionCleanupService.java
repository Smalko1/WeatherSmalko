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
        // Run a task every hour to clear outdated sessions
        scheduler.scheduleAtFixedRate(SessionCleanupService::cleanupExpiredSessions, 0, 1, TimeUnit.MINUTES);
    }

    static void cleanupExpiredSessions() {
        SessionService.getInstance().takingAndRemoveSessions();
    }
}
