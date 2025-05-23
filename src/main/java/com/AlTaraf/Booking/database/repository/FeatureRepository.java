package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {

}
