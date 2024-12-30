package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.StatusUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusUnit, Long> {

}
