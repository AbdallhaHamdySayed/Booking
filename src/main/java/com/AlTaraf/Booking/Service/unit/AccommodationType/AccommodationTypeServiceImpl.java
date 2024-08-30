package com.AlTaraf.Booking.Service.unit.AccommodationType;

import com.AlTaraf.Booking.Entity.unit.accommodationType.AccommodationType;
import com.AlTaraf.Booking.Repository.unit.AccommodationType.AccommodationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationTypeServiceImpl implements AccommodationTypeService {

    @Autowired
    AccommodationTypeRepository accommodationTypeRepository;


    @Override
    public List<AccommodationType> getAllAccommodationType() {
        return accommodationTypeRepository.findAll();
    }
}
