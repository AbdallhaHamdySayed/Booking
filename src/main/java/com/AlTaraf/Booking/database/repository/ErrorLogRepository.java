package com.AlTaraf.Booking.database.repository;


import com.AlTaraf.Booking.database.entity.ErrorLog;
import com.AlTaraf.Booking.database.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorLogRepository extends BaseRepository<Integer, ErrorLog> {
}
