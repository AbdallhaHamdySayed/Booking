package com.AlTaraf.Booking.Repository.unit.statusUnit;

import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusUnit, Long> {

}
