package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.UnitType;
import com.AlTaraf.Booking.database.repository.UnitTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitTypeService {

    @Autowired
    UnitTypeRepository unitTypeRepository;


    public List<UnitType> getAllUnitType() {
        return unitTypeRepository.findAll();
    }
}
