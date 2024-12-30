package com.AlTaraf.Booking.database.repository;


import com.AlTaraf.Booking.database.entity.UnitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitTypeRepository extends JpaRepository<UnitType, Long> {

}
