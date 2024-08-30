package com.AlTaraf.Booking.Mapper.Calender;

import com.AlTaraf.Booking.Dto.calender.Date.DateInfoHallsDto;
import com.AlTaraf.Booking.Dto.calender.Date.DateInfoHallsRequest;
import com.AlTaraf.Booking.Dto.calender.ReserveDateHallsDto;
import com.AlTaraf.Booking.Dto.calender.ReserveDateHallsRequest;
import com.AlTaraf.Booking.Entity.Calender.Halls.DateInfoHalls;
import com.AlTaraf.Booking.Entity.Calender.Halls.ReserveDateHalls;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReserveDateHallsMapper {
    ReserveDateHallsMapper INSTANCE = Mappers.getMapper(ReserveDateHallsMapper.class);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    @Mapping(source = "unit.id", target = "unitId")
//    @Mapping(source = "unit.accommodationType.id", target = "accommodationTypeId")
//    @Mapping(source = "unit.unitType.id", target = "unitTypeId")
    ReserveDateHallsRequest toDto(ReserveDateHalls reserveDateHalls);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    @Mapping(source = "unitId", target = "unit.id")
//    @Mapping(source = "accommodationTypeId", target = "unit.accommodationType.id")
//    @Mapping(source = "unitTypeId", target = "unit.unitType.id")
    ReserveDateHalls toEntity(ReserveDateHallsRequest reserveDateHallsDto);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    List<DateInfoHallsDto> mapDateInfoList(List<DateInfoHallsRequest> dateInfoList);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    List<DateInfoHallsRequest> mapDateInfoDtoList(List<DateInfoHallsDto> dateInfoList);

}
