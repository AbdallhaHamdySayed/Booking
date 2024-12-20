package com.AlTaraf.Booking.Payload.response.Reservation;

import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDtoSample;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDashboard {
    private Long reservationId;
    private String imagePath;
    private Long unitId;
    private String unitName;
    private CityDtoSample cityDto;
    private RegionDto regionDto;
    private int price;
    private Boolean isEvaluating;
    private String traderName;
    private String traderPhone;
    private String customerName;
    private String customerPhone;
    private String dateOfArrival;
    private String departureDate;
    private String createdDateFormatted;
    private String deviceToken;
}
