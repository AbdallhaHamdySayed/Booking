package com.AlTaraf.Booking.Repository.unit.FeatureForHalls;

import com.AlTaraf.Booking.Entity.unit.featureForHalls.FeatureForHalls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureForHallsRepository extends JpaRepository<FeatureForHalls, Long> {
}
