package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.AvailablePeriods;
import com.AlTaraf.Booking.database.repository.AvailablePeriodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailablePeriodsService {

    @Autowired
    AvailablePeriodsRepository availablePeriodsRepository;

    public List<AvailablePeriods> getAllAvailablePeriods() {
        return availablePeriodsRepository.findAll();
    }

}
