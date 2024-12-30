package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.database.entity.City;
import com.AlTaraf.Booking.rest.dto.CityDto;
import com.AlTaraf.Booking.rest.dto.CityDtoSample;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

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


