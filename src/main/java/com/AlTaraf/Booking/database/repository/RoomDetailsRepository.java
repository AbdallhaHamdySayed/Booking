package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.RoomDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface RoomDetailsRepository extends JpaRepository<RoomDetails, Long> {

    @Query("SELECT r FROM RoomDetails r WHERE r.unit.id = :unitId AND r.roomAvailable.id = :roomAvailable")
    RoomDetails findRoomDetailsByUnitIdAndRoomAvailableId(@Param("unitId") Long unitId, @Param("roomAvailable") Long roomAvailableId);


    @Query("SELECT COUNT(r) > 0 FROM RoomDetails r WHERE r.roomNumber = 0")
    boolean existsByRoomNumberZero();

    @Modifying
    @Transactional
    @Query("DELETE FROM RoomDetails rd WHERE rd.unit.id = :unitId")
    void deleteByUnitId(@Param("unitId") Long unitId);
}
