package com.AlTaraf.Booking.Service.cityAndRegion;

import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;

import java.util.List;

public interface RegionService {
    List<RegionDto> getRegionsByCityId(Long cityId);

    void deleteRegion(Long regionId);

    RegionDto addRegionToCity(Long cityId, RegionDto regionDto);
}
