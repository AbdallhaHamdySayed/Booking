package com.AlTaraf.Booking.Dto.calender.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateInfoDto {
    private Long id;
    private Date date;

}