package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.FeatureForHalls;
import com.AlTaraf.Booking.database.repository.FeatureForHallsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureForHallsService {

    @Autowired
    FeatureForHallsRepository featureForHallsRepository;

    public List<FeatureForHalls> getAllFeatureForHalls() {
        return featureForHallsRepository.findAll();
    }

}
