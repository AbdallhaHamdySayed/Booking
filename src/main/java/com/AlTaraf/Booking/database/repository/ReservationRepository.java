package com.AlTaraf.Booking.database.repository;


import com.AlTaraf.Booking.database.entity.AvailableArea;
import com.AlTaraf.Booking.database.entity.Reservations;
import com.AlTaraf.Booking.database.entity.RoomAvailable;
import com.AlTaraf.Booking.database.entity.Unit;
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

    @Query("SELECT r FROM Reservations r JOIN r.statusUnit s WHERE s.id = :statusUnitId")
    Page<Reservations> findByStatusUnitId(@Param("statusUnitId") Long statusUnitId, Pageable pageable);

    @Query("SELECT r FROM Reservations r WHERE r.unit.id = :unitId")
    Page<Reservations> findByUnitId(Long unitId, Pageable pageable);

    @Query("SELECT r FROM Reservations r JOIN r.statusUnit s " +
            "WHERE s.id = :statusId AND r.unit IN :units")
    Page<Reservations> getByStatusIdAndUnits(@Param("statusId") Long statusId,
                                             @Param("units") List<Unit> units,
                                             Pageable pageable);


    @Modifying
    @Transactional
    @Query("DELETE FROM Reservations r WHERE r.unit.id = :unitId")
    void deleteByUnitId(@Param("unitId") Long unitId);

    @Query("SELECT r FROM Reservations r WHERE r.departureDate <= :date AND r.user.id = :userId AND r.isEvaluating IS NULL")
    List<Reservations> findReservationsByDepartureDateBeforeAndUserIdAndNotEvaluating(@Param("date") LocalDate date, @Param("userId") Long userId);

    @Query("SELECT r FROM Reservations r WHERE r.user.id = :userId AND r.dateOfArrival >= :currentDate AND r.statusUnit.id = 2")
    Page<Reservations> findUserReservationsWithStatusAndLesserArrivalDate(
            @Param("userId") Long userId,
            @Param("currentDate") LocalDate currentDate,
            Pageable pageable);

    @Query("SELECT r FROM Reservations r WHERE r.user.id = :userId AND r.dateOfArrival < :currentDate AND r.statusUnit.id = 2")
    Page<Reservations> findUserReservationsWithStatusAndGreaterArrivalDate(
            @Param("userId") Long userId,
            @Param("currentDate") LocalDate currentDate,
            Pageable pageable);

    @Query("SELECT r FROM Reservations r " +
            "WHERE (r.unit.accommodationType.id IN (3, 4, 6) OR r.unit.unitType.id = 2) " +
            "AND r.unit.user.id = :userId " +
            "AND r.statusUnit.id = 1 " +
            "AND r.dateOfArrival = :dateOfArrival " +
            "AND r.departureDate = :departureDate")
    List<Reservations> findReservationsByCriteria(
            @Param("userId") Long userId,
            @Param("dateOfArrival") LocalDate dateOfArrival,
            @Param("departureDate") LocalDate departureDate);

    @Query("SELECT r FROM Reservations r " +
            "WHERE r.unit.user.id = :userId " +
            "AND r.statusUnit.id = 1 " +
            "AND r.dateOfArrival = :dateOfArrival " +
            "AND r.departureDate = :departureDate")
    List<Reservations> findReservationsByDate(
            @Param("userId") Long userId,
            @Param("dateOfArrival") LocalDate dateOfArrival,
            @Param("departureDate") LocalDate departureDate);
}
