package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.ReserveDateUnit;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveDateUnitRepository extends JpaRepository<ReserveDateUnit, Long> {
    @Query("SELECT rd FROM ReserveDateUnit rd WHERE rd.unit.id = :unitId")
    List<ReserveDateUnit> findListByUnitId(@Param("unitId") Long unitId);

    @Modifying
    @Transactional
    @Query("DELETE FROM DateInfo di WHERE di.reserveDateUnit.id = :reserveDateUnitId")
    void deleteDateInfoByReserveDateId(@Param("reserveDateUnitId") Long reserveDateId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ReserveDateUnit rd WHERE rd.unit.id = :unitId")
    void deleteByUnitId(@Param("unitId") Long unitId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ReserveDateUnit rd WHERE rd.reservations.id = :reservationsId")
    void deleteByReservationId(@Param("reservationsId") Long reservationsId);


     @Query("SELECT rdu FROM ReserveDateUnit rdu WHERE rdu.reservations.id = :reservationsId")
     ReserveDateUnit findByReservationId(Long reservationsId);
}
