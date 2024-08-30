package com.AlTaraf.Booking.Repository.Reservation;

import com.AlTaraf.Booking.Entity.Reservation.Reservations;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservations, Long> {

    List<Reservations> findAllReservationsByUserIdAndStatusUnitName(Long userId, String statusUnitName);

    @Query("SELECT r FROM Reservations r WHERE r.unit.id = :unitId")
    List<Reservations> findByUnitId(@Param("unitId") Long unitId);

    @Query("SELECT r.unit FROM Reservations r WHERE r.id = :reservationId")
    Unit findUnitByReservationId(@Param("reservationId") Long reservationId);

    @Query("SELECT r.availableArea FROM Reservations r WHERE r.id = :reservationId")
    AvailableArea findAvailableAreaIdByReservationId(@Param("reservationId") Long reservationId);

    @Query("SELECT r.roomAvailable FROM Reservations r WHERE r.id = :reservationId")
    RoomAvailable findRoomAvailableIdByReservationId(@Param("reservationId") Long reservationId);

    @Query("SELECT r FROM Reservations r JOIN r.user u JOIN r.statusUnit s WHERE u.id = :userId AND s.id = :statusUnitId")
    Page<Reservations> findByUserIdAndStatusUnitId(@Param("userId") Long userId, @Param("statusUnitId") Long statusUnitId, Pageable pageable);

    @Query("SELECT r FROM Reservations r WHERE r.unit.id = :unitId")
    Page<Reservations> findByUnitId(Long unitId, Pageable pageable);

    @Query("SELECT r FROM Reservations r JOIN r.statusUnit s WHERE s.id = :statusId AND r.unit.id = :unitId")
    Page<Reservations> findByStatusIdAndUnitId(@Param("statusId") Long statusId, @Param("unitId") Long unitId, Pageable pageable);

    boolean existsByUnitId(Long unitId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Reservations r WHERE r.unit.id = :unitId")
    void deleteByUnitId(@Param("unitId") Long unitId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Reservations r WHERE r.user = :user")
    void deleteByUser(@Param("user") User user);

    @Query("SELECT r FROM Reservations r WHERE r.departureDate <= :date AND r.user.id = :userId AND r.isEvaluating IS NULL")
    List<Reservations> findReservationsByDepartureDateBeforeAndUserIdAndNotEvaluating(@Param("date") LocalDate date, @Param("userId") Long userId);
}
