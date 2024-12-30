package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.database.entity.DateInfo;
import com.AlTaraf.Booking.database.entity.ReserveDate;
import com.AlTaraf.Booking.database.entity.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.rest.dto.DateInfoRequest;
import com.AlTaraf.Booking.rest.dto.ReserveDateDto;
import com.AlTaraf.Booking.rest.dto.ReserveDateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReserveDateMapper {

    ReserveDateMapper INSTANCE = Mappers.getMapper(ReserveDateMapper.class);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    @Mapping(source = "roomDetailsForAvailableAreaId", target = "roomDetailsForAvailableArea.id")
    @Mapping(source = "unitId", target = "unit.id")
    ReserveDate reserveDateRequestToReserveDate(ReserveDateRequest reserveDateRequest);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    @Mapping(source = "roomDetailsForAvailableArea.id", target = "roomDetailsForAvailableAreaId")
    @Mapping(source = "unit.id", target = "unitId")
    ReserveDateDto reserveDateToReserveDateRequest(ReserveDate reserveDate);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    List<DateInfo> mapDateInfoDtoList(List<DateInfoRequest> dateInfoList);


    default RoomDetailsForAvailableArea mapRoomDetailsForAvailableArea(Long roomDetailsForAvailableAreaId) {
        return roomDetailsForAvailableAreaId != null ? new RoomDetailsForAvailableArea(roomDetailsForAvailableAreaId) : null;
    }
}
