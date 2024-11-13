package com.AlTaraf.Booking.support.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static final ZoneId LIBYA_ZONE = ZoneId.of("Africa/Tripoli");


    public static LocalDateTime getCurrentDate() {
        ZonedDateTime zonedDateTime = LocalDateTime.now(LIBYA_ZONE).atZone(LIBYA_ZONE);
        ZonedDateTime systemZoneDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
        return systemZoneDateTime.toLocalDateTime();
    }



    public static LocalDateTime nowInLibya() {
        return LocalDateTime.now(LIBYA_ZONE);
    }

    public static Date getFirstDateOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }


}
