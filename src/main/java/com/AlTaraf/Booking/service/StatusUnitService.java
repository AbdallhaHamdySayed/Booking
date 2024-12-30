package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.StatusUnit;
import com.AlTaraf.Booking.database.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusUnitService {

    @Autowired
    StatusRepository statusUnitRepository;

    public List<StatusUnit> getAllStatusUnit() {
        return statusUnitRepository.findAll();
    }
}
