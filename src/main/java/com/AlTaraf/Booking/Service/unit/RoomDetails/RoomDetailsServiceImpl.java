package com.AlTaraf.Booking.Service.unit.RoomDetails;

import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsRepository;
import com.AlTaraf.Booking.Repository.unit.UnitRepository;
import com.AlTaraf.Booking.Repository.unit.roomAvailable.RoomAvailableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomDetailsServiceImpl implements RoomDetailsService{

    @Autowired
    RoomDetailsRepository roomDetailsRepository;

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    RoomAvailableRepository roomAvailableRepository;

    @Override
    public void addRoomDetails(Long unitId, Long roomAvailableId, RoomDetails roomDetails) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new RuntimeException("Unit not found with ID: " + unitId));

        RoomAvailable roomAvailable = roomAvailableRepository.findById(roomAvailableId)
                .orElseThrow(() -> new RuntimeException("RoomAvailable not found with ID: " + roomAvailableId));


        try {
//            if (unit.getRoomAvailableCount() == null) {
//                unit.setRoomAvailableCount(0);
//            }
//
//            Integer i = unit.getRoomAvailableCount();
//
//            System.out.println("i = " + i);
//
//            i++;
//
//            System.out.println("After Increment i = " + i);
//
//            unit.setRoomAvailableCount(i);

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

    @Override
    public RoomDetails getRoomDetailsByUnitIdAndRoomAvailableId(Long unitId, Long roomAvailableId) {
        return roomDetailsRepository.findRoomDetailsByUnitIdAndRoomAvailableId(unitId, roomAvailableId);
    }
}