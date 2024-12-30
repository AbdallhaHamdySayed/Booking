package com.AlTaraf.Booking.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class adsForSliderResponseDto {
    private Long adsId;
    private String imagePath;
    private Long unitId;
    private  String nameUnit;
    private CityDtoSample city;
    private RegionDto region;

}
