package com.AlTaraf.Booking.Repository.File;

import com.AlTaraf.Booking.Entity.File.FileForPdf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FileForPdfRepository extends JpaRepository<FileForPdf, String> {

    @Query("SELECT fp FROM FileForPdf fp WHERE fp.user.id = :userId AND fp.sentFlag IS NULL")
    FileForPdf findByUserIdAndSentFlagIsNull(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM FileForPdf p WHERE p.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}