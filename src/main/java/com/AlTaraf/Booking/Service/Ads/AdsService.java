package com.AlTaraf.Booking.Service.Ads;

import com.AlTaraf.Booking.Entity.Ads.Ads;
import com.AlTaraf.Booking.Entity.Ads.PackageAds;
import com.AlTaraf.Booking.Payload.response.Ads.adsForSliderResponseDto;
import com.AlTaraf.Booking.Payload.response.CounterAds;
import com.AlTaraf.Booking.Payload.response.CounterUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface AdsService {
    List<adsForSliderResponseDto> getAdsByStatusUnitId(Long statusUnitId);
    List<PackageAds> getAllPackageAds();
    Ads createAds(Ads ads);

    List<Ads> getAdsForUserAndStatus(Long userId, Long statusUnitId, Pageable pageable);

    void deleteAds(Long id);

    List<adsForSliderResponseDto> getAdsByAcceptedStatus();

    void updateStatusForAds(Long adsId, Long statusUnitId) throws IOException, InterruptedException;

    Ads findByUnitId(Long unitId);

//    Page<Ads> findAllAds(Pageable pageable);

    Page<Ads> findAllByStatusUnitId(Long statusUnitId, Pageable pageable);

    CounterAds getCountAds();

    Ads getByUserIdAndUnitId(Long userId, Long unitId);
}
