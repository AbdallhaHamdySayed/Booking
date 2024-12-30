package com.AlTaraf.Booking.rest.dto;


import com.AlTaraf.Booking.database.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionDto {
    private Long id;
    private String regionName;
    private String regionArabicName;

    public RegionDto(Region region) {
        this.id = region.getId();
        this.regionName = region.getRegionName();
        this.regionArabicName = region.getRegionArabicName();
    }
}