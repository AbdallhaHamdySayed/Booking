package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.AccommodationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationTypeRepository extends JpaRepository<AccommodationType, Long> {
}
