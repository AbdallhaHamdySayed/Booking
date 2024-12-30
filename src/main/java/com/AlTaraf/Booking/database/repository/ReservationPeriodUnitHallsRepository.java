package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.ReservationPeriodUnitHalls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationPeriodUnitHallsRepository extends JpaRepository<ReservationPeriodUnitHalls, Long> {

    @Query("SELECT r FROM ReservationPeriodUnitHalls r WHERE r.reservations.id = :reservationId")
    ReservationPeriodUnitHalls findByReservationId(@Param("reservationId") Long reservationId);

}
