package com.AlTaraf.Booking.rest.dto;

import com.AlTaraf.Booking.database.entity.AccommodationType;
import com.AlTaraf.Booking.database.entity.StatusUnit;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnitDashboard {
    private Long unitId;
    private Long userId;
    private String unitName;
    private AccommodationType accommodationType;
    private String traderName;
    private String traderPhone;
    private String traderEmail;
    private Boolean ban;
    private RegionDto regionDto;
    private CityDtoSample cityDtoSample;
    private StatusUnit statusUnit;
}
