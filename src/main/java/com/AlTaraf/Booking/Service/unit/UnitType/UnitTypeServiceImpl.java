package com.AlTaraf.Booking.Service.unit.UnitType;

import com.AlTaraf.Booking.Entity.unit.unitType.UnitType;
import com.AlTaraf.Booking.Repository.unit.UnitType.UnitTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitTypeServiceImpl implements UnitTypeService {

    @Autowired
    UnitTypeRepository unitTypeRepository;


    @Override
    public List<UnitType> getAllUnitType() {
        return unitTypeRepository.findAll();
    }
}
