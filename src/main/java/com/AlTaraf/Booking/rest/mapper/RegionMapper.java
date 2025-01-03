package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.database.entity.Region;
import com.AlTaraf.Booking.rest.dto.RegionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RegionMapper {
    RegionMapper INSTANCE = Mappers.getMapper(RegionMapper.class);

    @Mapping(source = "RegionDto.id", target = "id")
    @Mapping(source = "RegionDto.regionName", target = "regionName")
    @Mapping(source = "RegionDto.regionArabicName", target = "regionArabicName")
    Region updateDtoToEntity(RegionDto RegionDto, @MappingTarget Region existingRegion);

    @Mapping(source = "RegionDto.id", target = "id")
    @Mapping(source = "RegionDto.regionName", target = "regionName")
    @Mapping(source = "RegionDto.regionArabicName", target = "regionArabicName")
    Region dtoToEntity(RegionDto RegionDto);

    @Mapping(source = "region.id", target = "id")
    @Mapping(source = "region.regionName", target = "regionName")
    @Mapping(source = "region.regionArabicName", target = "regionArabicName")
    RegionDto entityToDto(Region region);
}
