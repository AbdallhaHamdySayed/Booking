package com.AlTaraf.Booking.Repository.File;

import com.AlTaraf.Booking.Entity.File.FileForUnit;
import com.AlTaraf.Booking.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileForUnitRepository extends JpaRepository<FileForUnit, String> {

    List<FileForUnit> findByUnitId(Long unitId);

    List<FileForUnit> findByUserId(Long userId);

    @Query("SELECT fu FROM FileForUnit fu WHERE fu.user.id = :userId AND fu.unit IS NULL")
    List<FileForUnit> findByUserIdAndUnitIsNull(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM FileForUnit fu WHERE fu.unit.id = :unitId")
    void deleteByUnitId(@Param("unitId") Long unitId);

    void deleteByUser(User user);
}