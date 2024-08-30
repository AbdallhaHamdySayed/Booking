package com.AlTaraf.Booking.Repository.unit.RoomDetails;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RoomDetailsRepository extends JpaRepository<RoomDetails, Long> {

    @Query("SELECT r FROM RoomDetails r WHERE r.unit.id = :unitId AND r.roomAvailable.id = :roomAvailable")
    RoomDetails findRoomDetailsByUnitIdAndRoomAvailableId(@Param("unitId") Long unitId, @Param("roomAvailable") Long roomAvailableId);

    @Query("SELECT r FROM RoomDetails r WHERE r.unit.id = :unitId ")
    List<RoomDetails> findRoomDetailsByUnitId(@Param("unitId") Long unitId);

    @Query("SELECT COUNT(r) > 0 FROM RoomDetails r WHERE r.roomNumber = 0")
    boolean existsByRoomNumberZero();

    boolean existsByUnitIdAndRoomAvailableId(Long unitId, Long roomAvailableId);

    @Modifying
    @Transactional
    @Query("DELETE FROM RoomDetails rd WHERE rd.unit.id = :unitId")
    void deleteByUnitId(@Param("unitId") Long unitId);
}
