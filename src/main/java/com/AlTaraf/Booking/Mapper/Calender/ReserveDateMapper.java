package com.AlTaraf.Booking.Mapper.Calender;

import com.AlTaraf.Booking.Dto.calender.Date.DateInfoDto;
import com.AlTaraf.Booking.Dto.calender.Date.DateInfoRequest;
import com.AlTaraf.Booking.Entity.Calender.DateInfo;
import com.AlTaraf.Booking.Entity.Calender.ReserveDate;
import com.AlTaraf.Booking.Entity.unit.availableArea.RoomDetailsForAvailableArea;
import com.AlTaraf.Booking.Payload.request.ReserveDate.ReserveDateDto;
import com.AlTaraf.Booking.Payload.request.ReserveDate.ReserveDateRequest;
import com.AlTaraf.Booking.Payload.request.ReserveDate.ReserveDateUnitDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
