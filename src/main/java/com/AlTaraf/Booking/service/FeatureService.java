package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.Feature;
import com.AlTaraf.Booking.database.repository.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureService {
    @Autowired
    FeatureRepository featureRepository;

    public List<Feature> getAllFeature() {
        return featureRepository.findAll();
    }
}
