package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.Ads;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {

    @Query("SELECT a FROM Ads a WHERE a.statusUnit.id = 2")
    List<Ads> findByStatusUnitId();

    @Query("SELECT a FROM Ads a WHERE a.user.id = :userId AND a.statusUnit.id = :statusUnitId")
    List<Ads> findAllAdsByUserIdAndStatusUnitId(@Param("userId") Long userId, @Param("statusUnitId") Long statusUnitId, Pageable pageable);
    List<Ads> findByStatusUnitName(String statusName);

    Ads findByUnitId(Long unitId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Ads a WHERE a.unit.id = :unitId")
    void deleteByUnitId(@Param("unitId") Long unitId);

    Page<Ads> findAllByStatusUnitId(Long statusUnitId, Pageable pageable);

    @Query("SELECT COUNT(a) FROM Ads a")
    Long countAllAds();

    @Query("SELECT COUNT(a) FROM Ads a JOIN a.statusUnit s WHERE s.id = 2")
    Long counterAcceptedAds();

    @Query("SELECT COUNT(a) FROM Ads a JOIN a.statusUnit s WHERE s.id = 3")
    Long counterRejectedAds();

    @Query("SELECT a FROM Ads a WHERE a.user.id = :userId AND a.unit.id = :unitId")
    Ads findByUserIdAndUnitId(@Param("userId") Long userId, @Param("unitId") Long unitId);

    @Query("SELECT COUNT(a) FROM Ads a WHERE a.user.id = :userId")
    Integer countAdsByUserId(@Param("userId") Long userId);
}
