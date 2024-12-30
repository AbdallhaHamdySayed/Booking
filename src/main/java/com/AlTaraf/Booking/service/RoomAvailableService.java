package com.AlTaraf.Booking.service;


import com.AlTaraf.Booking.database.entity.RoomAvailable;
import com.AlTaraf.Booking.database.repository.RoomAvailableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomAvailableService {

    @Autowired
    RoomAvailableRepository roomAvailableRepository;

    public List<RoomAvailable> getAllRoomAvailable() {
        return roomAvailableRepository.findAll();
    }

    public RoomAvailable getById(Long roomAvailableId) {
        return roomAvailableRepository.findById(roomAvailableId).orElse(null);
    }
}
