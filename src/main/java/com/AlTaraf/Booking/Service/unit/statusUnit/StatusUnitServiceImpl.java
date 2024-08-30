package com.AlTaraf.Booking.Service.unit.statusUnit;

import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.Repository.unit.UnitRepository;
import com.AlTaraf.Booking.Repository.unit.statusUnit.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusUnitServiceImpl implements StatusUnitService {
    @Autowired
    StatusRepository statusUnitRepository;

    @Autowired
    UnitRepository unitRepository;

    @Override
    public List<StatusUnit> getAllStatusUnit() {
        return statusUnitRepository.findAll();
    }

}
