package com.AlTaraf.Booking.Mapper.Reservation;


import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Entity.unit.feature.Feature;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.Entity.unit.subFeature.SubFeature;
import com.AlTaraf.Booking.Payload.request.Reservation.ReservationRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReservationRequestMapper {

    @Mapping(source = "clientName", target = "clientName")
    @Mapping(source = "clientPhone", target = "clientPhone")
    @Mapping(source = "deviceToken", target = "user.deviceToken")
    @Mapping(source = "unitId", target = "unit.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "roomAvailableId", target = "roomAvailable", qualifiedByName = "mapToRoomAvailable")
    @Mapping(source = "availableAreaId", target = "availableArea", qualifiedByName = "mapToAvailableArea")
//    @Mapping(source = "basicFeaturesIds", target = "basicFeaturesSet", qualifiedByName = "basicFeaturesIdsToEntities")
//    @Mapping(source = "subFeaturesIds", target = "subFeaturesSet", qualifiedByName = "subFeaturesIdsToEntities")
    @Mapping(source = "capacityHalls", target = "capacityHalls")
    @Mapping(source = "availablePeriodsHallsIds", target = "availablePeriodsHallsSet", qualifiedByName = "availablePeriodsHallsIdsToEntities")
//    @Mapping(source = "adultsAllowed", target = "adultsAllowed")
//    @Mapping(source = "childrenAllowed", target = "childrenAllowed")
//    @Mapping(source = "evaluationId", target = "evaluation.id")
    @Mapping(source = "dateOfArrival", target = "dateOfArrival")
    @Mapping(source = "departureDate", target = "departureDate")
    Reservations toReservation(ReservationRequestDto reservationRequestDto);

    List<ReservationRequestDto> toReservationsList(List<Reservations> reservationsList);

    @Named("mapToAvailableArea")
    static AvailableArea mapToAvailableArea(Long availableAreaId) {
        if (availableAreaId == null) {
            return null;
        }
        AvailableArea availableArea = new AvailableArea();
        availableArea.setId(availableAreaId);
        return availableArea;
    }
//    @Named("basicFeaturesIdsToEntities")
//    static Set<Feature> basicFeaturesIdsToEntities(Set<Long> basicFeaturesIds) {
//        if (basicFeaturesIds == null) {
//            return Collections.emptySet();
//        }
//        return basicFeaturesIds.stream()
//                .map(id -> {
//                    Feature feature = new Feature();
//                    feature.setId(id);
//                    return feature;
//                })
//                .collect(Collectors.toSet());
//    }

//    @Named("subFeaturesIdsToEntities")
//    static Set<SubFeature> subFeaturesIdsToEntities(Set<Long> subFeaturesIds) {
//        if (subFeaturesIds == null) {
//            return Collections.emptySet();
//        }
//        return subFeaturesIds.stream()
//                .map(id -> {
//                    SubFeature subFeature = new SubFeature();
//                    subFeature.setId(id);
//                    return subFeature;
//                })
//                .collect(Collectors.toSet());
//    }

    @Named("availablePeriodsHallsIdsToEntities")
    static Set<AvailablePeriods> availablePeriodsHallsToEntities(Set<Long> availablePeriodsHallsIds) {
        if (availablePeriodsHallsIds == null) {
            return Collections.emptySet();
        }
        return availablePeriodsHallsIds.stream()
                .map(id -> {
                    AvailablePeriods availablePeriods = new AvailablePeriods();
                    availablePeriods.setId(id);
                    return availablePeriods;
                })
                .collect(Collectors.toSet());
    }

    @Named("mapToRoomAvailable")
    static RoomAvailable mapToRoomAvailable(Long roomAvailableId) {
        if (roomAvailableId == null) {
            return null;
        }
        RoomAvailable roomAvailable = new RoomAvailable();
        roomAvailable.setId(roomAvailableId);
        return roomAvailable;
    }



}