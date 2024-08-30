package com.AlTaraf.Booking.Config;

import com.AlTaraf.Booking.Service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserCleanupScheduler {

    private final UserService userService;

    @Autowired
    public UserCleanupScheduler(UserService userService) {
        this.userService = userService;
    }

    // Schedule the task to run every day at midnight
    @Scheduled(cron = "0 0 * * * *")
    public void scheduleUserCleanup() {
        userService.deleteUsersWithIsActiveNull();
    }
}