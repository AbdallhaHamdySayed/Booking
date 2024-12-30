package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.AvailableArea;
import com.AlTaraf.Booking.database.entity.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.database.entity.Unit;
import com.AlTaraf.Booking.database.repository.AvailableAreaRepository;
import com.AlTaraf.Booking.database.repository.RoomDetailsForAvailableAreaRepository;
import com.AlTaraf.Booking.database.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomDetailsForAvailableAreaService {

    @Autowired
    RoomDetailsForAvailableAreaRepository roomDetailsForAvailableAreaRepository;

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    AvailableAreaRepository availableAreaRepository;

    public void addRoomDetails(Long unitId, Long roomAvailableId, RoomDetailsForAvailableArea roomDetailsForAvailableArea) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException("Unit not found with ID: " + unitId));

        AvailableArea availableArea = availableAreaRepository.findById(roomAvailableId)
                .orElseThrow(() -> new RuntimeException("RoomAvailable not found with ID: " + roomAvailableId));


        roomDetailsForAvailableArea.setUnit(unit);
        roomDetailsForAvailableArea.setAvailableArea(availableArea);

        if (unit.getPrice() == 0 ) {
            unit.setPrice(roomDetailsForAvailableArea.getNewPrice());
        }

        if (unit.getOldPrice() == 0) {
            unit.setOldPrice(roomDetailsForAvailableArea.getOldPrice());
        }

        roomDetailsForAvailableAreaRepository.save(roomDetailsForAvailableArea);
    }

    public RoomDetailsForAvailableArea getRoomDetailsByUnitIdAndAvailableAreaId(Long unitId, Long availableAreaId) {
        return roomDetailsForAvailableAreaRepository.findByUnitIdAndAvailableAreaId(unitId, availableAreaId);
    }
}
