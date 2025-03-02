package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.DateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DateInfoRepository extends JpaRepository<DateInfo, Long> {

    @Query("SELECT d FROM DateInfo d WHERE d.reserveDateUnit.id = :reserveDateUnitId")
    List<DateInfo> findByReserveDateUnitId(@Param("reserveDateUnitId") Long reserveDateUnitId);

}
