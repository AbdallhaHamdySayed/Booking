package com.AlTaraf.Booking.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateInfoHallsRequest {
    private LocalDate date;
    private boolean isEvening;
    private boolean isMorning;
}
