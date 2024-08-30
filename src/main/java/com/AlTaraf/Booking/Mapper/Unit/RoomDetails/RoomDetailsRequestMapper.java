package com.AlTaraf.Booking.Mapper.Unit.RoomDetails;

import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;
import com.AlTaraf.Booking.Payload.request.RoomDetails.RoomDetailsRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomDetailsRequestMapper {
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "roomAvailableId", target = "roomAvailable.id")
    @Mapping(source = "roomNumber", target = "roomNumber")
    @Mapping(source = "newPrice", target = "newPrice")
    @Mapping(source = "oldPrice", target = "oldPrice")
    @Mapping(source = "adultsAllowed", target = "adultsAllowed")
    @Mapping(source = "childrenAllowed", target = "childrenAllowed")
    RoomDetails toEntity(RoomDetailsRequestDto roomDetailsRequestDto);

//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "roomAvailableId", target = "roomAvailable.id")
    @Mapping(source = "roomNumber", target = "roomNumber")
    @Mapping(source = "newPrice", target = "newPrice")
    @Mapping(source = "oldPrice", target = "oldPrice")
    @Mapping(source = "adultsAllowed", target = "adultsAllowed")
    @Mapping(source = "childrenAllowed", target = "childrenAllowed")
    RoomDetailsForAvailableArea toEntityAvailableArea(RoomDetailsRequestDto roomDetailsRequestDto);


}
