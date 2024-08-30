package com.AlTaraf.Booking.Repository.technicalSupport;

import com.AlTaraf.Booking.Entity.TechnicalSupport.TechnicalSupport;
import com.AlTaraf.Booking.Entity.TechnicalSupport.TechnicalSupportForUnits;
import com.AlTaraf.Booking.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TechnicalSupportUnitRepository extends JpaRepository<TechnicalSupportForUnits, Long> {
    void deleteByUser(User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM TechnicalSupportForUnits tsfu WHERE tsfu.unit.id = :unitId")
    void deleteByUnitId(Long unitId);
}
