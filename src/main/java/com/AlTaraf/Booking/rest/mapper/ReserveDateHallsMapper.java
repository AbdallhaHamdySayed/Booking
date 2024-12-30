package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.database.entity.ReserveDateHalls;
import com.AlTaraf.Booking.rest.dto.DateInfoHallsDto;
import com.AlTaraf.Booking.rest.dto.DateInfoHallsRequest;
import com.AlTaraf.Booking.rest.dto.ReserveDateHallsRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReserveDateHallsMapper {
    ReserveDateHallsMapper INSTANCE = Mappers.getMapper(ReserveDateHallsMapper.class);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    @Mapping(source = "unit.id", target = "unitId")
    ReserveDateHallsRequest toDto(ReserveDateHalls reserveDateHalls);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    @Mapping(source = "unitId", target = "unit.id")
    ReserveDateHalls toEntity(ReserveDateHallsRequest reserveDateHallsDto);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    List<DateInfoHallsDto> mapDateInfoList(List<DateInfoHallsRequest> dateInfoList);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    List<DateInfoHallsRequest> mapDateInfoDtoList(List<DateInfoHallsDto> dateInfoList);

}
