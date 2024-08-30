package com.AlTaraf.Booking.Mapper.Image;

import com.AlTaraf.Booking.Dto.Image.FileForUnitDTO;
import com.AlTaraf.Booking.Entity.File.FileForUnit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileDataMapper {
    @Mapping(source = "type", target = "type")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "fileImageUrl", target = "fileImageUrl")
    @Mapping(source = "fileVideoUrl", target = "fileVideoUrl")
    FileForUnitDTO toDTO(FileForUnit fileForUnit);

    @Mapping(source = "type", target = "type")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "fileImageUrl", target = "fileImageUrl")
    @Mapping(source = "fileVideoUrl", target = "fileVideoUrl")
    FileForUnit toEntity(FileForUnitDTO fileForUnitDTO);
}