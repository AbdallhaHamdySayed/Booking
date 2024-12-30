package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.Schedule;
import com.AlTaraf.Booking.database.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends BaseRepository<Integer, Schedule> {
}
