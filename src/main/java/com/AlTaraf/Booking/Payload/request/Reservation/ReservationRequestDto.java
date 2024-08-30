package com.AlTaraf.Booking.Payload.request.Reservation;

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
//    private Set<Long> roomAvailableIds;
//    private Set<Long> availableAreaIds;
//    private Set<Long> basicFeaturesIds;
//    private Set<Long> subFeaturesIds;
//    private Set<Long> foodOptionsIds;
    private int capacityHalls;
    private Set<Long> availablePeriodsHallsIds;
//    private int adultsAllowed;
//    private int childrenAllowed;
//    private Long evaluationId;

    private LocalDate dateOfArrival;
    private LocalDate departureDate;
    private String deviceToken;

}
