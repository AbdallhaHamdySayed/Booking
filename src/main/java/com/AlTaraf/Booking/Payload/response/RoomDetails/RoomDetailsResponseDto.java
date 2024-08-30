package com.AlTaraf.Booking.Payload.response.RoomDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailsResponseDto {
    private Long roomId;
    private Long roomTypeId;
    private String name;
    private String arabicName;
    private int roomNumber;
    private BigDecimal newPrice;
    private BigDecimal oldPrice;
    private Long unitId;
    private int adultsAllowed;
    private int childrenAllowed;
    private boolean reserve;
}
