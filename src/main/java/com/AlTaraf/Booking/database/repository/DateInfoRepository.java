package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.DateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateInfoRepository extends JpaRepository<DateInfo, Long> {

}