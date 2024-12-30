package com.AlTaraf.Booking.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailsRequestDto {
    private int roomNumber;
    private Double newPrice;
    private Double oldPrice;
    private int adultsAllowed;
    private int childrenAllowed;
}
