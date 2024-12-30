package com.AlTaraf.Booking.rest.dto;

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
