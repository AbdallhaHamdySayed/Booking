package com.AlTaraf.Booking.Repository.unit.feature;

import com.AlTaraf.Booking.Entity.unit.feature.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {

}
