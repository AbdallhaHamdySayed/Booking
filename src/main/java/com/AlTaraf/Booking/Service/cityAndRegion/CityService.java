package com.AlTaraf.Booking.Service.cityAndRegion;

import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.saveCityDto;
import com.AlTaraf.Booking.Entity.cityAndregion.City;
import com.AlTaraf.Booking.Entity.cityAndregion.Region;

import java.util.List;
import java.util.Optional;

public interface CityService {
//    CityDto getCityByName(String cityName);
    List<CityDto> getAllCities();

    void deleteCity(Long cityId);

    City saveCityWithRegions(saveCityDto cityDto);

    Region updateRegionInCity(Long cityId, Long regionId, RegionDto RegionDto);

//    Region addRegionToCity(Long cityId, RegionDto regionDto);

    Optional<City> getCityById(Long cityId);

    CityDto createCity(CityDto cityDto);

    void save(City city);

    Optional<City> findById(Long id);

//    CityDto saveCity(CityDto cityDto);
//    CityDto updateCity(Long id, CityDto cityDto);
//    void deleteCity(Long id);

//    CityDto getCityById(Long id);

}

