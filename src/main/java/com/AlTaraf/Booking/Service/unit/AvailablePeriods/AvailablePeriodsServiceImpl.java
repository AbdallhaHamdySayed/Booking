package com.AlTaraf.Booking.Service.unit.AvailablePeriods;

import com.AlTaraf.Booking.Entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.Repository.AvailablePeriods.AvailablePeriodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailablePeriodsServiceImpl implements AvailablePeriodsService {

    @Autowired
    AvailablePeriodsRepository availablePeriodsRepository;

    @Override
    public List<AvailablePeriods> getAllAvailablePeriods() {
        return availablePeriodsRepository.findAll();
    }
}
