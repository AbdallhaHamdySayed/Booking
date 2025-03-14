package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.RoomDetailsForAvailableArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface RoomDetailsForAvailableAreaRepository extends JpaRepository<RoomDetailsForAvailableArea, Long> {
    @Query("SELECT r FROM RoomDetailsForAvailableArea r WHERE r.unit.id = :unitId AND r.availableArea.id = :availableAreaId")
    RoomDetailsForAvailableArea findByUnitIdAndAvailableAreaId(@Param("unitId") Long unitId, @Param("availableAreaId") Long availableAreaId);

    @Query("SELECT COUNT(r) > 0 FROM RoomDetailsForAvailableArea r WHERE r.roomNumber = 0")
    boolean existsByRoomNumberZero();

    @Modifying
    @Transactional
    @Query("DELETE FROM RoomDetailsForAvailableArea r WHERE r.unit.id = :unitId")
    void deleteByUnitId(@Param("unitId") Long unitId);

}
