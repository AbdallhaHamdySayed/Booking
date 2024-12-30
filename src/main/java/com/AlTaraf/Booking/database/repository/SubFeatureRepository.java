package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.SubFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubFeatureRepository extends JpaRepository<SubFeature, Long> {

}
