package com.AlTaraf.Booking.Payload.response.Reservation;

import com.AlTaraf.Booking.Entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.Entity.unit.accommodationType.AccommodationType;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Entity.unit.feature.Feature;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.Entity.unit.subFeature.SubFeature;
import com.AlTaraf.Booking.Entity.unit.unitType.UnitType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private LocalDate dateOfArrival;
    private LocalDate departureDate;
}
