package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.ReservationPeriodUnitHalls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationPeriodUnitHallsRepository extends JpaRepository<ReservationPeriodUnitHalls, Long> {

    @Query("SELECT r FROM ReservationPeriodUnitHalls r WHERE r.reservations.id = :reservationId")
    ReservationPeriodUnitHalls findByReservationId(@Param("reservationId") Long reservationId);

    @Query("SELECT r FROM ReservationPeriodUnitHalls r WHERE r.unit.id = :unitId")
    ReservationPeriodUnitHalls findByUnitId(@Param("unitId") Long unitId);

    @Query("SELECT r FROM ReservationPeriodUnitHalls r WHERE r.reservations.id = :reservationId AND r.availablePeriods.id = :availablePeriodsId")
    ReservationPeriodUnitHalls findByReservationIdAndAvailablePeriodsId(@Param("reservationId") Long reservationId, @Param("availablePeriodsId") Long availablePeriodsId);

    @Query("SELECT r FROM ReservationPeriodUnitHalls r WHERE r.availablePeriods.id = :availablePeriodsId")
    List<ReservationPeriodUnitHalls> findByAvailablePeriodsId(Long availablePeriodsId);
}
