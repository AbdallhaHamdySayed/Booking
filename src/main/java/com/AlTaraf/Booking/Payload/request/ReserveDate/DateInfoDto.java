package com.AlTaraf.Booking.Payload.request.ReserveDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateInfoDto {
    private String date;
    private boolean isEvening;
    private boolean isMorning;
}
