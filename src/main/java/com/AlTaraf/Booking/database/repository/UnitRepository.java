package com.AlTaraf.Booking.database.repository;


import com.AlTaraf.Booking.database.entity.City;
import com.AlTaraf.Booking.database.entity.Unit;
import com.AlTaraf.Booking.database.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long>, JpaSpecificationExecutor<Unit> {

    @Query("SELECT u FROM Unit u JOIN u.evaluation e WHERE u.statusUnit.id = 2 ORDER BY e.score DESC")
    Page<Unit> findByEvaluationInOrderByEvaluationScoreDesc(Pageable pageable);

    @Query(value = "SELECT * FROM unit WHERE STATUS_ID = 2 ORDER BY unit_id DESC LIMIT 5", nativeQuery = true)
    List<Unit> findByNewlyAdded();

    @Query("SELECT u FROM Unit u WHERE u.accommodationType.id = :accommodationTypeId AND u.statusUnit.id = 2")
    Page<Unit> findByAccommodationType_Id(Long accommodationTypeId, Pageable pageable);

    @Query("SELECT u FROM Unit u WHERE u.user.id = :userId AND u.statusUnit.id = :statusUnitId")
    Page<Unit> findAllByUserIdAndStatusUnitId(Long userId, Long statusUnitId, Pageable pageable);

    @Query("SELECT u FROM Unit u WHERE u.city = :city AND u.statusUnit.id = 2")
    Page<Unit> findByCity(City city, Pageable pageable);

    @Query("SELECT u FROM Unit u WHERE LOWER(u.nameUnit) LIKE LOWER(concat('%', :nameUnit, '%')) AND u.statusUnit.id = 2")
    Page<Unit> findByNameUnitContainingIgnoreCase(String nameUnit, Pageable pageable);

    @Query("SELECT u FROM Unit u WHERE LOWER(u.nameUnit) LIKE LOWER(concat('%', :nameUnit, '%'))")
    List<Unit> findByNameUnitContainingIgnoreCaseForMap(String nameUnit );

    @Query("SELECT u FROM Unit u WHERE u.unitType.id = :unitTypeId AND u.statusUnit.id = 2")
    Page<Unit> findByUnitType_Id(Long unitTypeId, Pageable pageable);

    @Query("SELECT u FROM Unit u WHERE u.unitType.id = :unitTypeId")
    Page<Unit> findByUnitType_IdForDashboard(Long unitTypeId, Pageable pageable);

    @Query("SELECT u FROM Unit u WHERE u.unitType.id = :unitTypeId")
    List<Unit> findByUnitType_IdForMap(@Param("unitTypeId") Long unitTypeId);

    @Query("SELECT u FROM Unit u WHERE u.user.id = :userId")
    Page<Unit> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT u FROM Unit u WHERE u.user.id = :userId")
    List<Unit> findByUserIdList(@Param("userId") Long userId );

    List<Unit> findAll(Specification<Unit> spec);

    @Query("SELECT u FROM Unit u WHERE LOWER(u.nameUnit) LIKE LOWER(concat('%', :nameUnit, '%')) AND u.unitType.id = :unitTypeId AND u.statusUnit.id = 2")
    Page<Unit> findByNameUnitAndUnitType(@Param("nameUnit") String nameUnit, @Param("unitTypeId") Long unitTypeId, Pageable pageable );

    @Query("SELECT u FROM Unit u WHERE LOWER(u.user.userName) LIKE LOWER(concat('%', :userName, '%')) AND u.unitType.id = :unitTypeId")
    Page<Unit> findByUsernameAndUnitType(@Param("userName") String username, @Param("unitTypeId") Long unitTypeId, Pageable pageable);

    @Query("SELECT u FROM Unit u WHERE LOWER(u.user.phone) LIKE LOWER(concat('%', :phoneNumber, '%')) AND u.unitType.id = :unitTypeId")
    Page<Unit> findByPhoneNumberAndUnitType(@Param("phoneNumber") String phoneNumber, @Param("unitTypeId") Long unitTypeId, Pageable pageable);

    @Query("SELECT u FROM Unit u WHERE u.nameUnit = :nameUnit AND u.unitType.id = :unitTypeId")
    List<Unit> findByNameUnitAndUnitTypeForMap(@Param("nameUnit") String nameUnit, @Param("unitTypeId") Long unitTypeId );

    @Query("SELECT u FROM Unit u JOIN u.roomAvailableSet ra WHERE LOWER(ra.arabicName) LIKE LOWER(concat('%', :roomAvailableName, '%')) AND u.statusUnit.id = 2")
    Page<Unit> findByRoomAvailableName(@Param("roomAvailableName") String roomAvailableName, Pageable pageable);

    @Query("SELECT u FROM Unit u INNER JOIN u.roomAvailableSet ra WHERE LOWER(u.nameUnit) LIKE LOWER(concat('%', :nameUnit, '%')) AND LOWER(ra.arabicName) LIKE LOWER(concat('%', :roomAvailableName, '%')) AND u.statusUnit.id = 2")
    Page<Unit> findByNameUnitAndRoomAvailableNameContainingIgnoreCase(String nameUnit, String roomAvailableName, Pageable pageable);

    @Query("SELECT u FROM Unit u JOIN u.availableAreaSet ra WHERE LOWER(ra.arabicName) LIKE LOWER(concat('%', :availableAreaName, '%')) AND u.statusUnit.id = 2")
    Page<Unit> findByAvailableAreaName(@Param("availableAreaName") String availableAreaName, Pageable pageable);

    @Query("SELECT u FROM Unit u INNER JOIN u.availableAreaSet ra WHERE LOWER(u.nameUnit) LIKE LOWER(concat('%', :nameUnit, '%')) AND LOWER(ra.arabicName) LIKE LOWER(concat('%', :availableAreaName, '%')) AND u.statusUnit.id = 2")
    Page<Unit> findByNameUnitAndAvailableAreaNameContainingIgnoreCase(String nameUnit, String availableAreaName, Pageable pageable);

    List<Unit> findByUser(User user);

    @Transactional
    @Modifying
    @Query("DELETE FROM Unit u WHERE u.user = :user")
    void deleteByUser(@Param("user") User user);

    @Query("SELECT u FROM Unit u WHERE u.statusUnit.id = 2")
    Page<Unit> findAllByStatusUnitId(Pageable pageable);

    @Query("SELECT COUNT(u) FROM Unit u WHERE u.unitType.id = 1")
    long countByAccommodationTypeIdNull();

    @Query("SELECT COUNT(u) FROM Unit u WHERE u.accommodationType.id = 1")
    long countByAccommodationTypeIdOne();
    @Query("SELECT COUNT(u) FROM Unit u WHERE u.accommodationType.id = 2")
    long countByAccommodationTypeIdTwo();
    @Query("SELECT COUNT(u) FROM Unit u WHERE u.accommodationType.id = 3")
    long countByAccommodationTypeIdThree();
    @Query("SELECT COUNT(u) FROM Unit u WHERE u.accommodationType.id = 4")
    long countByAccommodationTypeIdFour();
    @Query("SELECT COUNT(u) FROM Unit u WHERE u.accommodationType.id = 5")
    long countByAccommodationTypeIdFive();
    @Query("SELECT COUNT(u) FROM Unit u WHERE u.accommodationType.id = 6")
    long countByAccommodationTypeIdSix();


    @Query("""
    SELECT u FROM Unit u
    WHERE (:cityId IS NULL OR u.city.id = :cityId) 
    AND (:regionId IS NULL OR u.region.id = :regionId) 
    AND (:unitTypeId IS NULL OR u.unitType.id = :unitTypeId) 
    AND (:typesOfEventHallsId IS NULL OR u.typesOfEventHalls.id = :typesOfEventHallsId) 
    AND (:availablePeriodsHallsSetId IS NULL OR :availablePeriodsHallsSetId IN 
         (SELECT aph.id FROM u.availablePeriodsHallsSet aph)) 
    AND (:accommodationTypeIds IS NULL OR u.accommodationType.id IN :accommodationTypeIds) 
    AND (:hotelClassificationIds IS NULL OR u.hotelClassification.id IN :hotelClassificationIds) 
    AND (:basicFeaturesSetIds IS NULL OR 
         (SELECT COUNT(bf.id) FROM u.basicFeaturesSet bf WHERE bf.id IN :basicFeaturesSetIds) = 
         COALESCE(:#{#basicFeaturesSetIds?.size}, 0)) 
    AND (:featuresHallsSetIds IS NULL OR 
         (SELECT COUNT(fh.id) FROM u.featuresHallsSet fh WHERE fh.id IN :featuresHallsSetIds) = 
         COALESCE(:#{#featuresHallsSetIds?.size}, 0)) 
    AND (:subFeaturesSetIds IS NULL OR 
         (SELECT COUNT(sf.id) FROM u.subFeaturesSet sf WHERE sf.id IN :subFeaturesSetIds) = 
         COALESCE(:#{#subFeaturesSetIds?.size}, 0)) 
    AND (:evaluationIds IS NULL OR u.evaluation.id IN :evaluationIds) 
    AND (:minCapacityHalls IS NULL OR u.capacityHalls >= :minCapacityHalls) 
    AND (:maxCapacityHalls IS NULL OR u.capacityHalls <= :maxCapacityHalls) 
    AND (:minAdultsAllowed IS NULL OR u.adultsAllowed >= :minAdultsAllowed) 
    AND (:maxAdultsAllowed IS NULL OR u.adultsAllowed <= :maxAdultsAllowed) 
    AND (:minChildrenAllowed IS NULL OR u.childrenAllowed >= :minChildrenAllowed) 
    AND (:maxChildrenAllowed IS NULL OR u.childrenAllowed <= :maxChildrenAllowed) 
    AND (:priceMin IS NULL OR u.price >= :priceMin) 
    AND (:priceMax IS NULL OR u.price <= :priceMax) 
    AND u.dateOfArrival <= :departureDate 
    AND u.departureDate >= :dateOfArrival 
    AND u.id NOT IN ( 
        SELECT rdu.unit.id FROM ReserveDateUnit rdu 
        JOIN rdu.dateInfoList d 
        WHERE d.date >= :dateOfArrival AND d.date < :departureDate
    ) 

                AND NOT EXISTS (
                             SELECT 1 FROM ReserveDateHalls rdh
                             JOIN rdh.dateInfoList dih
                             WHERE rdh.unit.id = u.id
                             AND dih.date BETWEEN :dateOfArrival AND :departureDate
                        AND (:isMorning = TRUE AND dih.isMorning = TRUE OR :isEvening = TRUE AND dih.isEvening = TRUE)
                                                                              )
                          
                      
    AND u.statusUnit.id = 2
""")
    Page<Unit> findFilteringTwo(@Param("cityId") Long cityId,
                                @Param("regionId") Long regionId,
                                @Param("unitTypeId") Long unitTypeId,
                                @Param("typesOfEventHallsId") Long typesOfEventHallsId,
                                @Param("availablePeriodsHallsSetId") Long availablePeriodsHallsSetId,
                                @Param("accommodationTypeIds") Set<Long> accommodationTypeIds,
                                @Param("hotelClassificationIds") Set<Long> hotelClassificationIds,
                                @Param("basicFeaturesSetIds") Set<Long> basicFeaturesSetIds,
                                @Param("featuresHallsSetIds") Set<Long> featuresHallsSetIds,
                                @Param("subFeaturesSetIds") Set<Long> subFeaturesSetIds,
                                @Param("evaluationIds") Set<Long> evaluationIds,
                                @Param("minCapacityHalls") Integer minCapacityHalls,
                                @Param("maxCapacityHalls") Integer maxCapacityHalls,
                                @Param("minAdultsAllowed") Integer minAdultsAllowed,
                                @Param("maxAdultsAllowed") Integer maxAdultsAllowed,
                                @Param("minChildrenAllowed") Integer minChildrenAllowed,
                                @Param("maxChildrenAllowed") Integer maxChildrenAllowed,
                                @Param("priceMin") Integer priceMin,
                                @Param("priceMax") Integer priceMax,
                                @Param("isMorning") Boolean isMorning,
                                @Param("isEvening") Boolean isEvening,
                                @Param("dateOfArrival") LocalDate dateOfArrival,
                                @Param("departureDate") LocalDate departureDate,
                                Pageable pageable);


    @Query(value = "SELECT COUNT(unit_id) FROM available_periods_unit_halls WHERE unit_id = :unitId", nativeQuery = true)
    Long countUnitOccurrences(@Param("unitId") Long unitId);

    @Query("""
    SELECT u FROM Unit u
    JOIN ReserveDateHalls rdh ON rdh.unit.id = u.id
    JOIN DateInfoHalls dih ON dih.reserveDateHalls.id = rdh.id
    WHERE dih.date BETWEEN :dateOfArrival AND :departureDate
    AND (
        (:isMorning IS NOT NULL AND dih.isMorning = :isMorning) 
        OR (:isEvening IS NOT NULL AND dih.isEvening = :isEvening)
    )
    """)
    List<Unit> findUnitsByAvailability(
            @Param("dateOfArrival") LocalDate dateOfArrival,
            @Param("departureDate") LocalDate departureDate,
            @Param("isMorning") Boolean isMorning,
            @Param("isEvening") Boolean isEvening);

}