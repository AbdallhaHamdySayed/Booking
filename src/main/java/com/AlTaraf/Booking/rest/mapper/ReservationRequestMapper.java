package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.database.entity.AvailableArea;
import com.AlTaraf.Booking.database.entity.AvailablePeriods;
import com.AlTaraf.Booking.database.entity.Reservations;
import com.AlTaraf.Booking.database.entity.RoomAvailable;
import com.AlTaraf.Booking.rest.dto.ReservationRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
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
    @Mapping(source = "capacityHalls", target = "capacityHalls")
    @Mapping(source = "availablePeriodsHallsIds", target = "availablePeriodsHallsSet", qualifiedByName = "availablePeriodsHallsIdsToEntities")
    @Mapping(source = "dateOfArrival", target = "dateOfArrival")
    @Mapping(source = "departureDate", target = "departureDate")
    Reservations toReservation(ReservationRequestDto reservationRequestDto);

    @Named("mapToAvailableArea")
    static AvailableArea mapToAvailableArea(Long availableAreaId) {
        if (availableAreaId == null) {
            return null;
        }
        AvailableArea availableArea = new AvailableArea();
        availableArea.setId(availableAreaId);
        return availableArea;
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

}