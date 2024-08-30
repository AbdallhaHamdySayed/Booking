package com.AlTaraf.Booking.Repository.File;

import com.AlTaraf.Booking.Entity.File.FileForProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface FileForProfileRepository extends JpaRepository<FileForProfile, String> {


    @Modifying
    @Transactional
    @Query("UPDATE FileForProfile p SET p.user = null WHERE p.user.id = :userId")
    void disassociateByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM FileForProfile f WHERE f.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

}