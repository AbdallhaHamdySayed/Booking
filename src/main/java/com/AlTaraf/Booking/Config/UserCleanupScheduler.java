package com.AlTaraf.Booking.Config;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.Calender.ReserveDateRoomDetails2;
import com.AlTaraf.Booking.Entity.Schedule;
import com.AlTaraf.Booking.Repository.Ads.AdsRepository;
import com.AlTaraf.Booking.Service.ReserveDateRoomDetails2Service;
import com.AlTaraf.Booking.Service.ScheduleService;
import com.AlTaraf.Booking.Service.user.UserService;
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
    ReserveDateRoomDetails2Service reserveDateRoomDetails2Service;


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

//    @Scheduled(cron = "0 * * * * *")
//    public void hotelReserveDate() {
//        Schedule schedule = scheduleService.createEntity("HOTEL_RESERVE_DATE");
//        List<ReserveDateRoomDetails2> reserveDateRoomDetails2List = reserveDateRoomDetails2Service.listAll();
//        for (ReserveDateRoomDetails2 reserveDateRoomDetails2 : reserveDateRoomDetails2List) {
//            if (reserveDateRoomDetails2.getDate().isBefore(LocalDate.now())) {
//                System.out.println("reserveDateRoomDetails2.getUnit().getId(): " + reserveDateRoomDetails2.getUnit().getId());
//                reserveDateRoomDetails2Service.delete(reserveDateRoomDetails2);
//
//            }
//        }
//        scheduleService.updateEntity(schedule);
//    }


}