package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.database.entity.DateInfo;
import com.AlTaraf.Booking.database.entity.ReserveDateRoomDetails;
import com.AlTaraf.Booking.rest.dto.DateInfoRequest;
import com.AlTaraf.Booking.rest.dto.ReserveDateRequest;
import com.AlTaraf.Booking.rest.dto.ReserveDateRoomDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReserveDateRoomDetailsMapper {

    ReserveDateRoomDetailsMapper INSTANCE = Mappers.getMapper(ReserveDateRoomDetailsMapper.class);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    @Mapping(source = "roomDetailsId", target = "roomDetails.id")
    @Mapping(source = "unitId", target = "unit.id")
    ReserveDateRoomDetails reserveDateRequestToReserveDateRoomDetails(ReserveDateRequest reserveDateRequest);

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    @Mapping(source = "roomDetails.id", target = "roomDetailsId")
    @Mapping(source = "unit.id", target = "unitId")
    ReserveDateRoomDetailsDto reserveDateRoomDetailsToReserveDateRequest(ReserveDateRoomDetails reserveDateRoomDetails);

    @Named("toDateList")
    default List<Date> toDateList(List<Date> dates) {
        return dates.stream()
                .map(date -> new Date(date.getTime()))
                .collect(Collectors.toList());
    }

    @Mapping(source = "dateInfoList", target = "dateInfoList")
    List<DateInfo> mapDateInfoDtoList(List<DateInfoRequest> dateInfoList);

}
