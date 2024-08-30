package com.AlTaraf.Booking.Repository.ReserveDateRepository;

import com.AlTaraf.Booking.Entity.Calender.ReserveDateRoomDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveDateRoomDetailsRepository extends JpaRepository<ReserveDateRoomDetails, Long> {
    @Query("SELECT rd FROM ReserveDateRoomDetails rd WHERE rd.roomDetails.id = :roomDetailsId AND rd.unit.id = :unitId")
    List<ReserveDateRoomDetails> findByRoomDetailsIdAndUnitId(@Param("roomDetailsId") Long roomDetailsId, @Param("unitId") Long unitId);

    @Query("SELECT rd FROM ReserveDateRoomDetails rd WHERE rd.unit.id = :unitId")
    List<ReserveDateRoomDetails> findListByUnitId(@Param("unitId") Long unitId);

    @Modifying
    @Transactional
    @Query("DELETE FROM DateInfo di WHERE di.reserveDateRoomDetails.id = :reserveDateRoomDetailsId")
    void deleteDateInfoByReserveDateId(@Param("reserveDateRoomDetailsId") Long reserveDateRoomDetailsId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ReserveDateRoomDetails rd WHERE rd.unit.id = :unitId")
    void deleteByUnitId(@Param("unitId") Long unitId);
}
