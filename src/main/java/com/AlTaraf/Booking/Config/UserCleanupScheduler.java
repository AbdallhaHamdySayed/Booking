package com.AlTaraf.Booking.Config;

import com.AlTaraf.Booking.Entity.Schedule;
import com.AlTaraf.Booking.Service.ScheduleService;
import com.AlTaraf.Booking.Service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserCleanupScheduler {

    private final UserService userService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    public UserCleanupScheduler(UserService userService) {
        this.userService = userService;
    }

    // Schedule the task to run every day at midnight
    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleUserCleanup() {
        Schedule schedule = scheduleService.createEntity("DELETE_USER_NOT_ACTIVE_ACCOUNT");
        userService.deleteUsersWithIsActiveNull();
        scheduleService.updateEntity(schedule);
    }


}