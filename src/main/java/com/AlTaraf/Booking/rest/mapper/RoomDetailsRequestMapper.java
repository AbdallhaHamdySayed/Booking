package com.AlTaraf.Booking.rest.mapper;


import com.AlTaraf.Booking.database.entity.RoomDetails;
import com.AlTaraf.Booking.database.entity.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.rest.dto.RoomDetailsRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomDetailsRequestMapper {
    @Mapping(source = "roomNumber", target = "roomNumber")
    @Mapping(source = "newPrice", target = "newPrice")
    @Mapping(source = "oldPrice", target = "oldPrice")
    @Mapping(source = "adultsAllowed", target = "adultsAllowed")
    @Mapping(source = "childrenAllowed", target = "childrenAllowed")
    RoomDetails toEntity(RoomDetailsRequestDto roomDetailsRequestDto);

    @Mapping(source = "roomNumber", target = "roomNumber")
    @Mapping(source = "newPrice", target = "newPrice")
    @Mapping(source = "oldPrice", target = "oldPrice")
    @Mapping(source = "adultsAllowed", target = "adultsAllowed")
    @Mapping(source = "childrenAllowed", target = "childrenAllowed")
    RoomDetailsForAvailableArea toEntityAvailableArea(RoomDetailsRequestDto roomDetailsRequestDto);


}
