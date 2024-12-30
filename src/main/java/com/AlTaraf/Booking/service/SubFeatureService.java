package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.SubFeature;
import com.AlTaraf.Booking.database.repository.SubFeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubFeatureService {

    @Autowired
    SubFeatureRepository subFeatureRepository;

    public List<SubFeature> getAllSubFeature() {
        return subFeatureRepository.findAll();
    }
}
