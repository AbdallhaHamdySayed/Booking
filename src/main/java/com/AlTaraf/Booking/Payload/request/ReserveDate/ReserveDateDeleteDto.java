package com.AlTaraf.Booking.Payload.request.ReserveDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDateDeleteDto {
    private Long roomDetailsForAvailableAreaId;
    private Long roomDetailsId;
    private Long unitId;
}
