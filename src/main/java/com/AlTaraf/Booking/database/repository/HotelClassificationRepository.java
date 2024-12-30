package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.HotelClassification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelClassificationRepository extends JpaRepository<HotelClassification, Long> {

}
