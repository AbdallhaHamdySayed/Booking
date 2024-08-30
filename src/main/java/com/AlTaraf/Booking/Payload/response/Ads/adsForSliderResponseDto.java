package com.AlTaraf.Booking.Payload.response.Ads;

import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDtoSample;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class adsForSliderResponseDto {
    private Long adsId;
    private String imagePath;
//    private UnitDtoFavorite unitDtoFavorite;

    private Long unitId;

    private  String nameUnit;

    private CityDtoSample city;

    private RegionDto region;

}
