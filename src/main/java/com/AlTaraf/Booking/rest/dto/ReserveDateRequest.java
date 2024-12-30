package com.AlTaraf.Booking.rest.dto;

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
