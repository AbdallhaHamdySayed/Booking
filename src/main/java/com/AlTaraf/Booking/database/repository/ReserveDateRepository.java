package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.ReserveDate;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveDateRepository extends JpaRepository<ReserveDate, Long> {

    @Query("SELECT rd FROM ReserveDate rd WHERE rd.roomDetailsForAvailableArea.id = :roomDetailsForAvailableAreaId AND rd.unit.id = :unitId")
    List<ReserveDate> findByRoomDetailsForAvailableAreaIdAndUnitId(@Param("roomDetailsForAvailableAreaId") Long roomDetailsForAvailableAreaId, @Param("unitId") Long unitId);

    @Query("SELECT rd FROM ReserveDate rd WHERE rd.unit.id = :unitId")
    List<ReserveDate> findListByUnitId(@Param("unitId") Long unitId);

    @Modifying
    @Transactional
    @Query("DELETE FROM DateInfo di WHERE di.reserveDate.id = :reserveDateId")
    void deleteDateInfoByReserveDateId(@Param("reserveDateId") Long reserveDateId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ReserveDate rd WHERE rd.unit.id = :unitId")
    void deleteByUnitId(@Param("unitId") Long unitId);

}
