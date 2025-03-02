package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.DateInfoHalls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DateInfoHallsRepository extends JpaRepository<DateInfoHalls, Long> {
    @Query("SELECT d FROM DateInfoHalls d WHERE d.reserveDateHalls.id = :reserveDateHallsId")
    List<DateInfoHalls> findByreserveDateHallsId(@Param("reserveDateHallsId") Long reserveDateHallsId);

}
