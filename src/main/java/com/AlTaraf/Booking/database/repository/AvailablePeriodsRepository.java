package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.AvailablePeriods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailablePeriodsRepository extends JpaRepository<AvailablePeriods, Long> {
}
