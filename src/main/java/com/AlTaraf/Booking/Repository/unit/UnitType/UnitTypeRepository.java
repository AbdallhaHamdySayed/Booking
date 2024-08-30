package com.AlTaraf.Booking.Repository.unit.UnitType;

import com.AlTaraf.Booking.Entity.unit.unitType.UnitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitTypeRepository extends JpaRepository<UnitType, Long> {

}
