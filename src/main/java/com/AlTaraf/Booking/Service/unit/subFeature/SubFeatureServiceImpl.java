package com.AlTaraf.Booking.Service.unit.subFeature;

import com.AlTaraf.Booking.Entity.unit.subFeature.SubFeature;
import com.AlTaraf.Booking.Repository.unit.subFeature.SubFeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubFeatureServiceImpl implements SubFeatureService {

    @Autowired
    SubFeatureRepository subFeatureRepository;

    @Override
    public List<SubFeature> getAllSubFeature() {
        return subFeatureRepository.findAll();
    }
}
