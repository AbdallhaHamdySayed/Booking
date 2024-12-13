package com.AlTaraf.Booking.Repository;

import com.AlTaraf.Booking.Entity.Schedule;
import com.AlTaraf.Booking.Repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends BaseRepository<Integer, Schedule> {
}
