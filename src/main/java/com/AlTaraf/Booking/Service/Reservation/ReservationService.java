package com.AlTaraf.Booking.Service.Reservation;

import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.Exception.InsufficientFundsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    Reservations saveReservation(Long userId, Reservations reservations) throws InsufficientFundsException;

    Reservations getReservationById(Long id); // Add this method to get a reservation by ID

    List<Reservations> getReservationsForUserAndStatus(Long userId, String statusUnitName);

    List<Reservations> findReservationByUnitId(Long unitId);

    Unit findUnitByReservationId(Long reservationId);

    void updateStatusForReservation(Long reservationId, Long statusUnitId);

    AvailableArea getAvailableAreaByReservations(Long reservationId);

    RoomAvailable getRoomAvailableByReservations(Long reservationId);

    Page<Reservations> getReservationForUserAndStatus(Long userId, Long statusUnitId , Pageable pageable);

    void changeStatusUnitId(Long reservationId, Long newStatusUnitId);

    void deleteUnit(Long id);

    Page<Reservations> findByUnitId(Long unitId, Pageable pageable);

    Page<Reservations> getByStatusIdAndUnitId(Long statusId, Long unitId, Pageable pageable);

    void setCommissionForAllReservations(Double commission);

    List<Reservations> findReservationsByDepartureDateBeforeAndUserIdAndNotEvaluating(LocalDate date, Long userId);
}
