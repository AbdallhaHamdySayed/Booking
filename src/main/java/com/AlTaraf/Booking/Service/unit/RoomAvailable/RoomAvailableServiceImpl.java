package com.AlTaraf.Booking.Service.unit.RoomAvailable;


import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.Repository.unit.roomAvailable.RoomAvailableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomAvailableServiceImpl implements RoomAvailableService {

    @Autowired
    RoomAvailableRepository roomAvailableRepository;

    @Override
    public List<RoomAvailable> getAllRoomAvailable() {
        return roomAvailableRepository.findAll();
    }

    @Override
    public RoomAvailable getById(Long roomAvailableId) {
        return roomAvailableRepository.findById(roomAvailableId).orElse(null);
    }
}