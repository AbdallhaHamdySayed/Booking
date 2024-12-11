package com.AlTaraf.Booking.Repository;

import com.AlTaraf.Booking.Entity.Calender.DateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateInfoRepository extends JpaRepository<DateInfo, Long> {

}
