package com.AlTaraf.Booking.Dto.calender;

import com.AlTaraf.Booking.Dto.calender.Date.DateInfoHallsDto;
import com.AlTaraf.Booking.Dto.calender.Date.DateInfoHallsRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDateHallsRequest {
    private List<DateInfoHallsRequest> dateInfoList;
    private Long unitId;
//    private Long accommodationTypeId;
//    private Long unitTypeId;

}