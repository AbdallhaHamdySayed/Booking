package com.AlTaraf.Booking.Repository.Ads;

import com.AlTaraf.Booking.Entity.Ads.PackageAds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageAdsRepository extends JpaRepository<PackageAds, Long> {

}