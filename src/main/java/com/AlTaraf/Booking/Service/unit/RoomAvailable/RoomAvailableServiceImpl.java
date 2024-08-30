package com.AlTaraf.Booking.Service.unit.RoomAvailable;


import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.Repository.unit.RoomDetails.RoomDetailsRepository;
import com.AlTaraf.Booking.Repository.unit.roomAvailable.RoomAvailableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//
//import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomAvailable;
//import com.AlTaraf.Booking.entity.unit.roomAvailable.RoomDetails;
//import com.AlTaraf.Booking.repository.unit.RoomDetails.RoomDetailsRepository;
//import com.AlTaraf.Booking.repository.unit.roomAvailable.RoomAvailableRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.List;
@Service
public class RoomAvailableServiceImpl implements RoomAvailableService {

    @Autowired
    private RoomDetailsRepository roomDetailsRepository;

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

//   @Override
//    public List<RoomAvailable> getRoomAvailableByIds(List<Long> roomAvailableIds) {
//       return roomAvailableRepository.findAllById(roomAvailableIds);
//   }
//
//    @Override
//    public void insertRoomDetails(Long roomAvailableId, int roomNumber, BigDecimal newPrice, BigDecimal oldPrice) {
//        // Step 1: Create an instance of RoomDetails
//        RoomDetails roomDetails = new RoomDetails();
//        roomDetails.setRoomNumber(roomNumber);
//        roomDetails.setNewPrice(newPrice);
//        roomDetails.setOldPrice(oldPrice);
//
//        // Step 2: Set the relationship with the corresponding RoomAvailable instance
//        RoomAvailable roomAvailable = roomAvailableRepository.findById(roomAvailableId).orElse(null);
//        roomDetails.setRoomAvailable(roomAvailable);
//
//        // Step 3: Add the RoomDetails instance to the roomDetailsList in RoomAvailable
//        if (roomAvailable != null) {
//            roomAvailable.getRoomDetailsList().add(roomDetails);
//        }
//
//        // Step 4: Save the RoomAvailable instance to persist the changes
//        roomAvailableRepository.save(roomAvailable);
//    }
//}
