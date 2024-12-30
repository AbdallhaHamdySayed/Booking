package com.AlTaraf.Booking.rest.dto;

import com.AlTaraf.Booking.database.entity.PackageAds;
import com.AlTaraf.Booking.database.entity.StatusUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsResponseStatusDto {
    private Long id;
    private String imagePath;
    private Long unitId;
    private Long userId;

    private Long unitTypeId;
    private PackageAds packageAds;
    private String unitName;
    private CityDtoSample cityDto;
    private RegionDto regionDto;
    private StatusUnit statusUnit;
}
