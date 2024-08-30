package com.AlTaraf.Booking.Mapper.Unit.RoomDetails;

import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomDetails;
import com.AlTaraf.Booking.Payload.request.RoomDetails.RoomDetailsRequestDto;
import com.AlTaraf.Booking.Payload.response.RoomDetails.RoomDetailsResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomDetailsResponseMapper {

//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "roomNumber", target = "roomNumber")
//    @Mapping(source = "newPrice", target = "newPrice")
//    @Mapping(source = "oldPrice", target = "oldPrice")
//    @Mapping(source = "adultsAllowed", target = "adultsAllowed")
//    @Mapping(source = "childrenAllowed", target = "childrenAllowed")
//    RoomDetails toEntity(RoomDetailsResponseDto roomDetailsResponseDto);

    @Mapping(source = "id", target = "roomId")
    @Mapping(source = "roomAvailable.id", target = "roomTypeId")
    @Mapping(source = "roomAvailable.name", target = "name")
    @Mapping(source = "roomAvailable.arabicName", target = "arabicName")
    @Mapping(source = "roomNumber", target = "roomNumber")
    @Mapping(source = "newPrice", target = "newPrice")
    @Mapping(source = "oldPrice", target = "oldPrice")
    @Mapping(source = "adultsAllowed", target = "adultsAllowed")
    @Mapping(source = "childrenAllowed", target = "childrenAllowed")
    @Mapping(source = "unit.id", target = "unitId")
    RoomDetailsResponseDto toDto(RoomDetails roomDetails);

    @Mapping(source = "id", target = "roomId")
    @Mapping(source = "availableArea.id", target = "roomTypeId")
    @Mapping(source = "availableArea.name", target = "name")
    @Mapping(source = "availableArea.arabicName", target = "arabicName")
    @Mapping(source = "roomNumber", target = "roomNumber")
    @Mapping(source = "newPrice", target = "newPrice")
    @Mapping(source = "oldPrice", target = "oldPrice")
    @Mapping(source = "adultsAllowed", target = "adultsAllowed")
    @Mapping(source = "childrenAllowed", target = "childrenAllowed")
    @Mapping(source = "unit.id", target = "unitId")
    RoomDetailsResponseDto toDtoForAvailableArea(RoomDetailsForAvailableArea roomDetailsForAvailableArea);



}
