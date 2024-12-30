package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.TechnicalSupportForUnits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TechnicalSupportUnitRepository extends JpaRepository<TechnicalSupportForUnits, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM TechnicalSupportForUnits tsfu WHERE tsfu.unit.id = :unitId")
    void deleteByUnitId(Long unitId);
}
