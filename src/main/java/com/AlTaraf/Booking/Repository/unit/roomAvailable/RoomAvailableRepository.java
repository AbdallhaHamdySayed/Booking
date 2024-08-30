package com.AlTaraf.Booking.Repository.unit.roomAvailable;

import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomAvailableRepository extends JpaRepository<RoomAvailable, Long>  {
}
