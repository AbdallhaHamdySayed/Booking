package com.AlTaraf.Booking.config;


import com.AlTaraf.Booking.service.ScheduleService;
import com.AlTaraf.Booking.database.entity.Ads;
import com.AlTaraf.Booking.database.entity.Schedule;
import com.AlTaraf.Booking.database.repository.AdsRepository;
import com.AlTaraf.Booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class UserCleanupScheduler {

    private final UserService userService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    AdsRepository adsRepository;


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

    @Scheduled(cron = "0 0 0 * * *")
    public void adsDeletedExpired() {
        Schedule schedule = scheduleService.createEntity("DELETE_ADS_EXPIRED");
        List<Ads> adsList = adsRepository.findByStatusUnitId();
        for (Ads ads : adsList) {
            if (ads.getDateAds().isBefore(LocalDate.now())) {
                adsRepository.delete(ads);
            }
        }
        scheduleService.updateEntity(schedule);
    }
}