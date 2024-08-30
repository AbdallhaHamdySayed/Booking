package com.AlTaraf.Booking.Payload.request.ReserveDate;

import com.AlTaraf.Booking.Dto.calender.Date.DateInfoDto;
import com.AlTaraf.Booking.Dto.calender.Date.DateInfoRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDateRequest {
    private List<DateInfoRequest> dateInfoList;
    private Long roomDetailsForAvailableAreaId;
    private Long roomDetailsId;
    private Long unitId;
}
