package com.AlTaraf.Booking.Repository.AvailablePeriods;

import com.AlTaraf.Booking.Entity.unit.AvailablePeriods.AvailablePeriods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailablePeriodsRepository  extends JpaRepository<AvailablePeriods, Long> {
}
