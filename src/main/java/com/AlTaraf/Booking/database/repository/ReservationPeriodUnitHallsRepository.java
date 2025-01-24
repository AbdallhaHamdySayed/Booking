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

    @Query("SELECT r FROM ReservationPeriodUnitHalls r WHERE r.unit.id = :unitId AND r.availablePeriods.id = :availablePeriodsId AND r.statusUnit.id = 2")
    List<ReservationPeriodUnitHalls> findByUnitIdAndAvailableAndAccepted(@Param("unitId") Long unitId, @Param("availablePeriodsId") Long availablePeriodsId);

    @Query("SELECT r FROM ReservationPeriodUnitHalls r WHERE r.unit.id = :unitId AND r.availablePeriods.id = :availablePeriodsId AND r.statusUnit.id = 1")
    List<ReservationPeriodUnitHalls> findByUnitIdAndAvailableAndPended(@Param("unitId") Long unitId, @Param("availablePeriodsId") Long availablePeriodsId);
}
