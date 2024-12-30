package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.PackageAds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageAdsRepository extends JpaRepository<PackageAds, Long> {

}