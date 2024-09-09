package com.AlTaraf.Booking.Service.unit.RoomAvailable;

import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;

import java.util.List;

public interface RoomAvailableService {
    List<RoomAvailable> getAllRoomAvailable();

    RoomAvailable getById(Long roomAvailableId);
}
