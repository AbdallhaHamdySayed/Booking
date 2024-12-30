package com.AlTaraf.Booking.config.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class DateUtils {

    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return java.util.Date.from(startOfDay.atZone(java.time.ZoneId.systemDefault()).toInstant());
    }

    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return java.util.Date.from(endOfDay.atZone(java.time.ZoneId.systemDefault()).toInstant());
    }
}