package com.AlTaraf.Booking.Repository.unit;

import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.cityAndregion.City;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
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

import java.util.Date;
import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long>, JpaSpecificationExecutor<Unit> {

    Page<Unit> findByFavoriteTrue(Pageable pageable);

    Page<Unit> findByHotelClassification_NameIn(List<String> hotelClassificationNames, Pageable pageable);

    @Query("SELECT u FROM Unit u JOIN u.evaluation e WHERE u.statusUnit.id = 2 ORDER BY e.score DESC")
    Page<Unit> findByEvaluationInOrderByEvaluationScoreDesc(Pageable pageable);

//    @Query("SELECT u FROM Unit u WHERE u.statusUnit.id = 2 AND u.createdDate BETWEEN :startOfDay AND :endOfDay")
@Query(value = "SELECT * FROM unit WHERE STATUS_ID = 2 ORDER BY unit_id DESC LIMIT 5", nativeQuery = true)
List<Unit> findByNewlyAdded();

    Page<Unit> findByStatusUnit_Name(String name, Pageable pageable);

    @Query("SELECT u FROM Unit u WHERE u.accommodationType.id = :accommodationTypeId AND u.statusUnit.id = 2")
    Page<Unit> findByAccommodationType_Id(Long accommodationTypeId, Pageable pageable);

//    Page<Unit> findByNameUnitContainingIgnoreCase(String nameUnit, Pageable pageable);

    List<Unit> findAllByUserIdAndStatusUnitName(Long userId, String statusUnitName);
    @Query("SELECT u FROM Unit u WHERE u.user.id = :userId AND u.statusUnit.id = :statusUnitId")
    Page<Unit> findAllByUserIdAndStatusUnitId(Long userId, Long statusUnitId, Pageable pageable);

    Page<Unit> findByUser_IdAndFavorite(Long userId, boolean favorite, Pageable pageable);

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

    Page<Unit> findByUserId(Long userId, Pageable pageable);

    List<Unit> findAll(Specification<Unit> spec);

    @Query("SELECT u FROM Unit u JOIN u.roomAvailableSet ra WHERE ra = :roomAvailable")
    List<Unit> findByRoomAvailable(@Param("roomAvailable") RoomAvailable roomAvailable);


    @Query("SELECT u FROM Unit u WHERE LOWER(u.nameUnit) LIKE LOWER(concat('%', :nameUnit, '%')) AND u.unitType.id = :unitTypeId AND u.statusUnit.id = 2")
    Page<Unit> findByNameUnitAndUnitType(@Param("nameUnit") String nameUnit, @Param("unitTypeId") Long unitTypeId, Pageable pageable );

    @Query("SELECT u FROM Unit u WHERE LOWER(u.user.username) LIKE LOWER(concat('%', :username, '%')) AND u.unitType.id = :unitTypeId")
    Page<Unit> findByUsernameAndUnitType(@Param("username") String username, @Param("unitTypeId") Long unitTypeId, Pageable pageable);

    @Query("SELECT u FROM Unit u WHERE LOWER(u.user.phone) LIKE LOWER(concat('%', :phoneNumber, '%')) AND u.unitType.id = :unitTypeId")
    Page<Unit> findByPhoneNumberAndUnitType(@Param("phoneNumber") String phoneNumber, @Param("unitTypeId") Long unitTypeId, Pageable pageable);

    @Query("SELECT u FROM Unit u WHERE u.nameUnit = :nameUnit AND u.unitType.id = :unitTypeId")
    List<Unit> findByNameUnitAndUnitTypeForMap(@Param("nameUnit") String nameUnit, @Param("unitTypeId") Long unitTypeId );

//    Page<Unit> findByRoomAvailableSet_NameContainingIgnoreCase(String roomAvailableName, Pageable pageable);

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
}