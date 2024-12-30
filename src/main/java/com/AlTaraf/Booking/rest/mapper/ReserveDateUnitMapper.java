package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.database.entity.DateInfo;
import com.AlTaraf.Booking.database.entity.ReserveDateUnit;
import com.AlTaraf.Booking.rest.dto.DateInfoRequest;
import com.AlTaraf.Booking.rest.dto.ReserveDateRequest;
import com.AlTaraf.Booking.rest.dto.ReserveDateUnitDto;
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
