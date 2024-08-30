package com.AlTaraf.Booking.Mapper.Calender;

import com.AlTaraf.Booking.Dto.calender.Date.DateInfoDto;
import com.AlTaraf.Booking.Dto.calender.Date.DateInfoRequest;
import com.AlTaraf.Booking.Entity.Calender.DateInfo;
import com.AlTaraf.Booking.Entity.Calender.ReserveDateUnit;
import com.AlTaraf.Booking.Payload.request.ReserveDate.ReserveDateRequest;
import com.AlTaraf.Booking.Payload.request.ReserveDate.ReserveDateUnitDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReserveDateUnitMapper {

    ReserveDateUnitMapper INSTANCE = Mappers.getMapper(ReserveDateUnitMapper.class);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    @Mapping(source = "unitId", target = "unit.id")
    ReserveDateUnit reserveDateRequestToReserveDate(ReserveDateRequest reserveDateRequest);


    @Mapping(source = "dateInfoList", target = "dateInfoList")
    @Mapping(source = "unit.id", target = "unitId")
    ReserveDateUnitDto reserveDateUnitToReserveDateRequest(ReserveDateUnit reserveDate);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    List<DateInfo> mapDateInfoDtoList(List<DateInfoRequest> dateInfoList);
}
