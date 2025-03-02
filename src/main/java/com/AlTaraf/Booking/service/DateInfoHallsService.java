package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.DateInfoHalls;
import com.AlTaraf.Booking.database.repository.DateInfoHallsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DateInfoHallsService {

    @Autowired
    DateInfoHallsRepository dateInfoHallsRepository;

    public void save(DateInfoHalls dateInfoHalls) {
        dateInfoHallsRepository.save(dateInfoHalls);
    }

    public void delete(DateInfoHalls dateInfoHalls) {
        dateInfoHallsRepository.delete(dateInfoHalls);
    }

    public List<DateInfoHalls> getDateInfoHallsByReserveDateHallsId(Long reserveDateHallsId ) {
        return dateInfoHallsRepository.findByreserveDateHallsId(reserveDateHallsId);
    }

}
