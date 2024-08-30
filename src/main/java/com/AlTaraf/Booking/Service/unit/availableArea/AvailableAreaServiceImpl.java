package com.AlTaraf.Booking.Service.unit.availableArea;

import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Repository.unit.availableArea.AvailableAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailableAreaServiceImpl implements AvailableAreaService {

    @Autowired
    AvailableAreaRepository availableAreaRepository;

    @Override
    public List<AvailableArea> getAllAvailableArea() {
        return availableAreaRepository.findAll();
    }

    @Override
    public AvailableArea save(AvailableArea availableArea) {
        return availableAreaRepository.save(availableArea);
    }

    @Override
    public AvailableArea getById(Long availableAreaId) {
        return availableAreaRepository.findById(availableAreaId).orElse(null);
    }
}
