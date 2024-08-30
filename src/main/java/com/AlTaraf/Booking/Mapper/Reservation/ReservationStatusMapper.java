package com.AlTaraf.Booking.Mapper.Reservation;


import com.AlTaraf.Booking.Entity.File.FileForUnit;
import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Payload.response.Reservation.ReservationStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReservationStatusMapper {
    @Mapping(source = "id", target = "reservationId")
    @Mapping(source = "user.deviceToken", target = "deviceToken")
    @Mapping(target = "imagePaths", expression = "java(extractFilePaths(reservation.getUnit().getFileForUnits()))")
//    @Mapping(target = "videoPaths", expression = "java(extractFirstFileVideoPath(reservation.getUnit().getFileForUnits()))")
    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(source = "unit.nameUnit", target = "unitName")
    @Mapping(source = "unit.city", target = "cityDto")
    @Mapping(source = "unit.region", target = "regionDto")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "isEvaluating", target = "isEvaluating")
    @Mapping(source = "dateOfArrival", target = "dateOfArrival")
    @Mapping(source = "departureDate", target = "departureDate")
    ReservationStatus toReservationStatusDto(Reservations reservation);

    List<ReservationStatus> toReservationStatusDtoList(List<Reservations> reservationsList);

    public default List<String> extractFilePaths(List<FileForUnit> fileForUnits) {
        return fileForUnits.stream()
                .map(FileForUnit::getFileImageUrl)
                .collect(Collectors.toList());
    }

//    default String extractFirstFileVideoPath(List<FileForUnit> fileForUnits) {
//        if (fileForUnits == null || fileForUnits.isEmpty()) {
//            return null; // or return a default value if preferred
//        }
//        return fileForUnits.get(0).getFileVideoUrl();
//    }

}
