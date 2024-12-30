package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.HotelClassification;
import com.AlTaraf.Booking.database.repository.HotelClassificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelClassificationService {

    @Autowired
    HotelClassificationRepository hotelClassificationRepository;

    public List<HotelClassification> getAllHotelClassification() {
        return hotelClassificationRepository.findAll();
    }
}
