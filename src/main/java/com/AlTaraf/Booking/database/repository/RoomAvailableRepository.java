package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.RoomAvailable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomAvailableRepository extends JpaRepository<RoomAvailable, Long>  {
}
