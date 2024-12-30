package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.ReserveDateHalls;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveDateHallsRepository extends JpaRepository<ReserveDateHalls, Long> {

    @Query("SELECT rd FROM ReserveDateHalls rd WHERE rd.unit.id = :unitId AND rd.reserve = true")
    List<ReserveDateHalls> findByUnitIdAndReserveIsTrue(@Param("unitId") Long unitId);

    @Query("SELECT rd FROM ReserveDateHalls rd WHERE rd.unit.id = :unitId")
    List<ReserveDateHalls> findListByUnitId(@Param("unitId") Long unitId);

    @Query("SELECT rd FROM ReserveDateHalls rd WHERE rd.unit.id = :unitId")
    ReserveDateHalls findByUnitId(@Param("unitId") Long unitId);

    @Modifying
    @Transactional
    @Query("DELETE FROM DateInfoHalls dih WHERE dih.reserveDateHalls.id IN (SELECT rdh.id FROM ReserveDateHalls rdh WHERE rdh.unit.id = :unitId)")
    void deleteRelatedDateInfoHallsByUnitId(@Param("unitId") Long unitId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ReserveDateHalls rdh WHERE rdh.unit.id = :unitId")
    void deleteByUnitId(@Param("unitId") Long unitId);

    @Modifying
    @Transactional
    @Query("DELETE FROM DateInfoHalls di WHERE di.reserveDateHalls.id = :reserveDateHallsId")
    void deleteDateInfoHallsByReserveDateHallsId(@Param("reserveDateHallsId") Long reserveDateHallsId);
}
