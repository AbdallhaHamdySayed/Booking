package com.AlTaraf.Booking.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
