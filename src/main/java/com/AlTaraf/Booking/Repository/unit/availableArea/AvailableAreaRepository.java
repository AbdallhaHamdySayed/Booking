package com.AlTaraf.Booking.Repository.unit.availableArea;

import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AvailableAreaRepository extends JpaRepository<AvailableArea, Long> {

}
