package com.AlTaraf.Booking.Dto.calender.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateInfoHallsRequest {
    private Date date;
    private boolean isEvening;
    private boolean isMorning;

}
