package com.AlTaraf.Booking.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDto {
    private String clientName;
    private String clientPhone;
    private Long unitId;
    private Long userId;
    private Long roomAvailableId;
    private Long availableAreaId;
    private int capacityHalls;
    private Set<Long> availablePeriodsHallsIds;
    private LocalDate dateOfArrival;
    private LocalDate departureDate;
    private String deviceToken;

}
