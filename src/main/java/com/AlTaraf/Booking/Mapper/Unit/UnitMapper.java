package com.AlTaraf.Booking.Mapper.Unit;

import com.AlTaraf.Booking.Dto.Unit.UnitDto;
import com.AlTaraf.Booking.Entity.File.FileForUnit;
import com.AlTaraf.Booking.Entity.unit.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UnitMapper {

    @Mappings({
            @Mapping(source = "unit.id", target = "unitId"),
            @Mapping(source = "unitType.id", target = "unitTypeId"),
            @Mapping(target = "images", expression = "java(extractFileImagePaths(unit.getFileForUnits()))"),
            @Mapping(target = "video", expression = "java(extractFirstFileVideoPath(unit.getFileForUnits()))"),
            @Mapping(source = "unit.nameUnit", target = "nameUnit"),
            @Mapping(source = "unit.city.cityName", target = "cityName"),
            @Mapping(source = "unit.region.regionName", target = "regionName"),
            @Mapping(source = "unit.city.arabicCityName", target = "arabicCityName"),
            @Mapping(source = "unit.region.regionArabicName", target = "regionArabicName"),
            @Mapping(source = "price", target = "price", defaultValue = "0"),
            @Mapping(source = "oldPrice", target = "oldPrice", defaultValue = "0")
    })
    UnitDto toUnitDto(Unit unit);

    List<UnitDto> toUnitDtoList(List<Unit> units);

    // Define a method to extract file paths from ImageData entities
    default List<String> extractFileImagePaths(List<FileForUnit> fileForUnits) {
        if (fileForUnits == null || fileForUnits.isEmpty()) {
            return Collections.emptyList();
        }
        return fileForUnits.stream()
                .sorted(Comparator.comparing(FileForUnit::getCreatedDate)
                        .thenComparing(FileForUnit::getCreatedTime))
                .map(FileForUnit::getFileImageUrl)
                .filter(Objects::nonNull) // To ensure no null file paths are included
                .collect(Collectors.toList());
    }


    default String extractFirstFileVideoPath(List<FileForUnit> fileForUnits) {
        if (fileForUnits == null || fileForUnits.isEmpty()) {
            return null; // or return a default value if preferred
        }
        return fileForUnits.get(0).getFileVideoUrl();
    }
}
