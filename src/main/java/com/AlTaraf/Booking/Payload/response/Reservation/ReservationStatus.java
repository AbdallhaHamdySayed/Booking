package com.AlTaraf.Booking.Payload.response.Reservation;

import com.AlTaraf.Booking.Dto.Image.FileForUnitDTO;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDtoSample;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationStatus {
    private Long reservationId;
    private List<String> imagePaths;
    private String videoPaths;
    private Long unitId;
    private String unitName;
    private CityDtoSample cityDto;
    private RegionDto regionDto;
    private int price;
    private Boolean isEvaluating;
    private LocalDate dateOfArrival;
    private LocalDate departureDate;
    private String deviceToken;
}
