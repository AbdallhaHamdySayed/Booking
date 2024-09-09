package com.AlTaraf.Booking.Service.cityAndRegion;

import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.saveCityDto;
import com.AlTaraf.Booking.Entity.cityAndregion.City;
import com.AlTaraf.Booking.Entity.cityAndregion.Region;

import java.util.List;
import java.util.Optional;

public interface CityService {
    List<CityDto> getAllCities();

    void deleteCity(Long cityId);

    Region updateRegionInCity(Long cityId, Long regionId, RegionDto RegionDto);

    CityDto createCity(CityDto cityDto);

    void save(City city);

    Optional<City> findById(Long id);


}

