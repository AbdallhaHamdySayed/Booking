package com.AlTaraf.Booking.Dto.Unit;

import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDtoSample;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.Entity.unit.accommodationType.AccommodationType;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
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
