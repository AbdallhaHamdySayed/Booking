package com.AlTaraf.Booking.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDateForHallsDto {
    private Long id;
    private List<DateInfoHallsDto> dateInfoList;
    private Long unitId;
    private boolean reserve;
}
