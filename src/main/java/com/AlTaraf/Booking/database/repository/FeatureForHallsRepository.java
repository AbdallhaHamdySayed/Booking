package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.FeatureForHalls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureForHallsRepository extends JpaRepository<FeatureForHalls, Long> {
}
