package com.AlTaraf.Booking.Repository.File;

import com.AlTaraf.Booking.Entity.File.FileForAds;
import com.AlTaraf.Booking.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileForAdsRepository extends JpaRepository<FileForAds, String> {

    List<FileForAds> findByUserId(Long userId);

    @Query("SELECT fa FROM FileForAds fa WHERE fa.user.id = :userId AND fa.ads IS NULL")
    List<FileForAds> findByUserIdAndAdsIsNull(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM FileForAds fa WHERE fa.ads.id = :adsId")
    void deleteByAdsId(@Param("adsId") Long adsId);

    void deleteByUser(User user);

    FileForAds findByUnitId(Long unitId);

    @Modifying
    @Query("DELETE FROM FileForAds i WHERE i.unit.id = :unitId")
    void deleteByUnitId(@Param("unitId") Long unitId);
}