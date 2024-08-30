package com.AlTaraf.Booking.Service.unit.feature;

import com.AlTaraf.Booking.Entity.unit.feature.Feature;
import com.AlTaraf.Booking.Repository.unit.feature.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    FeatureRepository featureRepository;

    @Override
    public List<Feature> getAllFeature() {
        return featureRepository.findAll();
    }
}
