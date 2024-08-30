package com.AlTaraf.Booking.Repository.unit.HotelClassification;

import com.AlTaraf.Booking.Entity.unit.hotelClassification.HotelClassification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelClassificationRepository extends JpaRepository<HotelClassification, Long> {

}
