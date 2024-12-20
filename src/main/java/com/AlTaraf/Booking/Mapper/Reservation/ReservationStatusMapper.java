package com.AlTaraf.Booking.Mapper.Reservation;


import com.AlTaraf.Booking.Entity.File.FileForUnit;
import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Payload.response.Reservation.ReservationDashboard;
import com.AlTaraf.Booking.Payload.response.Reservation.ReservationStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationStatusMapper {
    @Mapping(source = "id", target = "reservationId")
    @Mapping(source = "user.deviceToken", target = "deviceToken")
    @Mapping(target = "imagePath", expression = "java(extractFilePaths(reservation.getUnit().getFileForUnits()))")
    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(source = "unit.nameUnit", target = "unitName")
    @Mapping(source = "unit.city", target = "cityDto")
    @Mapping(source = "unit.region", target = "regionDto")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "isEvaluating", target = "isEvaluating")
    @Mapping(source = "clientName", target = "customerName")
    @Mapping(source = "clientPhone", target = "customerPhone")
    @Mapping(source = "dateOfArrival", target = "dateOfArrival", qualifiedByName = "formatDate")
    @Mapping(source = "departureDate", target = "departureDate", qualifiedByName = "formatDate")
    ReservationStatus toReservationStatusDto(Reservations reservation);

    @Mapping(source = "id", target = "reservationId")
    @Mapping(source = "user.deviceToken", target = "deviceToken")
    @Mapping(target = "imagePath", expression = "java(extractFilePaths(reservation.getUnit().getFileForUnits()))")
    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(source = "unit.nameUnit", target = "unitName")
    @Mapping(source = "unit.city", target = "cityDto")
    @Mapping(source = "unit.region", target = "regionDto")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "isEvaluating", target = "isEvaluating")
    @Mapping(source = "unit.user.username", target = "traderName")
    @Mapping(source = "unit.user.phone", target = "traderPhone")
    @Mapping(source = "clientName", target = "customerName")
    @Mapping(source = "clientPhone", target = "customerPhone")
    @Mapping(source = "dateOfArrival", target = "dateOfArrival", qualifiedByName = "formatDate")
    @Mapping(source = "departureDate", target = "departureDate", qualifiedByName = "formatDate")
    @Mapping(source = "createdDate", target = "createdDateFormatted", qualifiedByName = "formatDateTime")
    ReservationDashboard toReservationDashboardDto(Reservations reservation);

    List<ReservationStatus> toReservationStatusDtoList(List<Reservations> reservationsList);
    List<ReservationDashboard> toReservationDashboardDto(List<Reservations> reservationsList);

    @Named("formatDateTime")
    default String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    default String extractFilePaths(List<FileForUnit> fileForUnits) {
        if (fileForUnits == null || fileForUnits.isEmpty()) {
            return null; // or return a default value if preferred
        }
        return fileForUnits.stream()
                .filter(file -> file.getFileImageUrl() != null)
                .sorted(Comparator.comparing(FileForUnit::getCreatedDate)
                        .thenComparing(FileForUnit::getCreatedTime))
                .map(FileForUnit::getFileImageUrl)
                .findFirst()
                .orElse(null);
    }

    @Named("formatDate")
    static String formatDate(LocalDate date) {
        return date != null ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }
}
