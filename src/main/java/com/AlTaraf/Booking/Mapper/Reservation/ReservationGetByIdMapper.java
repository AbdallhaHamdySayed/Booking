package com.AlTaraf.Booking.Mapper.Reservation;

import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Entity.unit.feature.Feature;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.Entity.unit.subFeature.SubFeature;
import com.AlTaraf.Booking.Payload.response.Reservation.ReservationResponseGetId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReservationGetByIdMapper {
    @Mapping(source = "reservation.id", target = "reservationId")
    @Mapping(source = "clientName", target = "clientName")
    @Mapping(source = "clientPhone", target = "clientPhone")
    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(source = "unit.nameUnit", target = "unitName")
    @Mapping(source = "unit.unitType", target = "unitType")
    @Mapping(source = "unit.accommodationType", target = "accommodationType")
    @Mapping(source = "user.id", target = "userId")
//    @Mapping(source = "roomAvailable", target = "roomAvailable", qualifiedByName = "mapToRoomAvailable")
    @Mapping(source = "roomAvailable", target = "roomAvailable")
//    @Mapping(source = "availableArea", target = "availableArea", qualifiedByName = "mapToAvailableArea")
    @Mapping(source = "availableArea", target = "availableArea")
    @Mapping(source = "basicFeaturesSet", target = "basicFeatures")
    @Mapping(source = "subFeaturesSet", target = "subFeatures")
    @Mapping(source = "capacityHalls", target = "capacityHalls")
    @Mapping(source = "availablePeriodsHallsSet", target = "availablePeriodsHalls")
    @Mapping(source = "adultsAllowed", target = "adultsAllowed")
    @Mapping(source = "childrenAllowed", target = "childrenAllowed")
    @Mapping(source = "evaluation.id", target = "evaluationId")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "dateOfArrival", target = "dateOfArrival")
    @Mapping(source = "departureDate", target = "departureDate")
    ReservationResponseGetId toReservationDto(Reservations reservation);

    List<ReservationResponseGetId> toReservationsDtoList(List<Reservations> reservations);

    @Named("basicFeaturesIdsToEntities")
    static Set<Feature> basicFeaturesIdsToEntities(Set<Long> basicFeaturesIds) {
        if (basicFeaturesIds == null) {
            return Collections.emptySet();
        }
        return basicFeaturesIds.stream()
                .map(id -> {
                    Feature feature = new Feature();
                    feature.setId(id);
                    return feature;
                })
                .collect(Collectors.toSet());
    }

    @Named("subFeaturesIdsToEntities")
    static Set<SubFeature> subFeaturesIdsToEntities(Set<Long> subFeaturesIds) {
        if (subFeaturesIds == null) {
            return Collections.emptySet();
        }
        return subFeaturesIds.stream()
                .map(id -> {
                    SubFeature subFeature = new SubFeature();
                    subFeature.setId(id);
                    return subFeature;
                })
                .collect(Collectors.toSet());
    }

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

    @Named("mapToAvailableArea")
    static AvailableArea mapToAvailableArea(Long availableAreaId) {
        if (availableAreaId == null) {
            return null;
        }
        AvailableArea availableArea = new AvailableArea();
        availableArea.setId(availableAreaId);
        return availableArea;
    }

    @Named("featuresToIds")
    static Set<Long> featuresToIds(Set<Feature> features) {
        if (features == null) {
            return Collections.emptySet();
        }
        return features.stream()
                .map(Feature::getId)
                .collect(Collectors.toSet());
    }

    @Named("periodsToIds")
    static Set<Long> periodsToIds(Set<AvailablePeriods> periods) {
        if (periods == null) {
            return Collections.emptySet();
        }
        return periods.stream()
                .map(AvailablePeriods::getId)
                .collect(Collectors.toSet());
    }


}
