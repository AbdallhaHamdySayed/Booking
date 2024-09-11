package com.AlTaraf.Booking.Service.unit.RoomDetailsForAvailableArea;

import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsForAvailableAreaRepository;
import com.AlTaraf.Booking.Repository.unit.UnitRepository;
import com.AlTaraf.Booking.Repository.unit.availableArea.AvailableAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomDetailsForAvailableAreaServiceImpl implements RoomDetailsForAvailableAreaService {

    @Autowired
    RoomDetailsForAvailableAreaRepository roomDetailsForAvailableAreaRepository;

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    AvailableAreaRepository availableAreaRepository;

    @Override
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

    @Override
    public RoomDetailsForAvailableArea getRoomDetailsByUnitIdAndAvailableAreaId(Long unitId, Long availableAreaId) {
        return roomDetailsForAvailableAreaRepository.findByUnitIdAndAvailableAreaId(unitId, availableAreaId);
    }
}
