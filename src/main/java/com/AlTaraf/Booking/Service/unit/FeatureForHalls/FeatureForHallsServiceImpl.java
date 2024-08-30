package com.AlTaraf.Booking.Service.unit.FeatureForHalls;

import com.AlTaraf.Booking.Entity.unit.featureForHalls.FeatureForHalls;
import com.AlTaraf.Booking.Repository.unit.FeatureForHalls.FeatureForHallsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureForHallsServiceImpl implements FeatureForHallsService {

    @Autowired
    FeatureForHallsRepository featureForHallsRepository;

    @Override
    public List<FeatureForHalls> getAllFeatureForHalls() {
        return featureForHallsRepository.findAll();
    }
}
