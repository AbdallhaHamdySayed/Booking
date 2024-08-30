package com.AlTaraf.Booking.Mapper.city;

import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDtoSample;
import com.AlTaraf.Booking.Entity.cityAndregion.City;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDto;
import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {

CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    @Mapping(source = "cityName", target = "cityName")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "arabicCityName", target = "arabicCityName")
    CityDto cityToCityDTO(City city);

    @Mapping(source = "cityName", target = "cityName")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "arabicCityName", target = "arabicCityName")
    City cityDTOToCity(CityDto cityDto);


    @Mapping(source = "cityName", target = "cityName")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "arabicCityName", target = "arabicCityName")
    CityDtoSample cityToCityDTOSample(City city);

    @Mapping(source = "cityName", target = "cityName")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "arabicCityName", target = "arabicCityName")
    City cityDTOSampleToCity(CityDtoSample cityDto);

    @Mapping(source = "cityName", target = "cityName")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "arabicName", target = "arabicName")
    List<CityDto> citiesToCityDTOs(List<City> cities);

}


