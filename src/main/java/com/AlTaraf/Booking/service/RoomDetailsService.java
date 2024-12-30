package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.RoomAvailable;
import com.AlTaraf.Booking.database.entity.RoomDetails;
import com.AlTaraf.Booking.database.entity.Unit;
import com.AlTaraf.Booking.database.repository.RoomAvailableRepository;
import com.AlTaraf.Booking.database.repository.RoomDetailsRepository;
import com.AlTaraf.Booking.database.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomDetailsService {

    @Autowired
    RoomDetailsRepository roomDetailsRepository;

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    RoomAvailableRepository roomAvailableRepository;

    public void addRoomDetails(Long unitId, Long roomAvailableId, RoomDetails roomDetails) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException("Unit not found with ID: " + unitId));

        RoomAvailable roomAvailable = roomAvailableRepository.findById(roomAvailableId)
                .orElseThrow(() -> new RuntimeException("RoomAvailable not found with ID: " + roomAvailableId));


        try {

            roomDetails.setUnit(unit);
            roomDetails.setRoomAvailable(roomAvailable);

            if (unit.getPrice() == 0) {
                unit.setPrice(roomDetails.getNewPrice());
            }

            if (unit.getOldPrice() == 0) {
                unit.setOldPrice(roomDetails.getOldPrice());
            }

            roomDetailsRepository.save(roomDetails);
        } catch (Exception e) {
            System.out.println("Exception Error: " + e);
        }
    }

    public RoomDetails getRoomDetailsByUnitIdAndRoomAvailableId(Long unitId, Long roomAvailableId) {
        return roomDetailsRepository.findRoomDetailsByUnitIdAndRoomAvailableId(unitId, roomAvailableId);
    }
}
