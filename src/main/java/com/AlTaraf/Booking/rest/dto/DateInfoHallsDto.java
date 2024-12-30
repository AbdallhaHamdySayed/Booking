package com.AlTaraf.Booking.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateInfoHallsDto {
    private Long id;
    private Date date;
    private boolean isEvening;
    private boolean isMorning;

}
