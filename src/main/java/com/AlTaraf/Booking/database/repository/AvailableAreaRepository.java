package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.AvailableArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AvailableAreaRepository extends JpaRepository<AvailableArea, Long> {

}