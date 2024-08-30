package com.AlTaraf.Booking.Payload.request.ReserveDate;

import com.AlTaraf.Booking.Dto.calender.Date.DateInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDateRoomDetailsDto {
    private List<DateInfoDto> dateInfoList;
    private Long roomDetailsId;
    private Long unitId;
}
