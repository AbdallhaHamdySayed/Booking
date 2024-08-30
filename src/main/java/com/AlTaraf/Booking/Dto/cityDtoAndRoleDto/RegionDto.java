package com.AlTaraf.Booking.Dto.cityDtoAndRoleDto;


import com.AlTaraf.Booking.Entity.cityAndregion.Region;
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

    // Constructor to map Region entity to RegionDto
    public RegionDto(Region region) {
        this.id = region.getId();
        this.regionName = region.getRegionName();
        this.regionArabicName = region.getRegionArabicName();
    }
}