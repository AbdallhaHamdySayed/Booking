package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.AvailableArea;
import com.AlTaraf.Booking.database.repository.AvailableAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailableAreaService {

    @Autowired
    AvailableAreaRepository availableAreaRepository;

    public List<AvailableArea> getAllAvailableArea() {
        return availableAreaRepository.findAll();
    }

    public AvailableArea save(AvailableArea availableArea) {
        return availableAreaRepository.save(availableArea);
    }

    public AvailableArea getById(Long availableAreaId) {
        return availableAreaRepository.findById(availableAreaId).orElse(null);
    }
}
