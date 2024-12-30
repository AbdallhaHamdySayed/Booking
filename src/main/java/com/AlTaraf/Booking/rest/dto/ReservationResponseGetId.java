package com.AlTaraf.Booking.rest.dto;

import com.AlTaraf.Booking.database.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseGetId {
    private Long reservationId;
    private Long unitId;
    private String unitName;
    private UnitType unitType;
    private AccommodationType accommodationType;
    private Long userId;
    private String clientName;
    private String clientPhone;
    private RoomAvailable roomAvailable;
    private AvailableArea availableArea;
    private Set<Feature> basicFeatures;
    private Set<SubFeature> subFeatures;
    private int capacityHalls;
    private Set<AvailablePeriods> availablePeriodsHalls;
    private int adultsAllowed;
    private int childrenAllowed;
    private Long evaluationId;
    private int price;
    private String dateOfArrival;
    private String departureDate;
}
