package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.AccommodationType;
import com.AlTaraf.Booking.database.repository.AccommodationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationTypeService {

    @Autowired
    AccommodationTypeRepository accommodationTypeRepository;


    public List<AccommodationType> getAllAccommodationType() {
        return accommodationTypeRepository.findAll();
    }
}
