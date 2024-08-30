package com.AlTaraf.Booking.Mapper.Unit;

import com.AlTaraf.Booking.Dto.Unit.UnitDtoFavorite;
import com.AlTaraf.Booking.Entity.File.FileForUnit;
import com.AlTaraf.Booking.Entity.unit.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UnitFavoriteMapper {

    @Mappings({
            @Mapping(source = "unit.id", target = "unitId"),
            @Mapping(source = "unitType.id", target = "unitTypeId"),
            @Mapping(target = "imagePath", expression = "java(extractFirstFileImagePath(unit.getFileForUnits()))"),
            @Mapping(target = "videoPath", expression = "java(extractFirstFileVideoPath(unit.getFileForUnits()))"),
            @Mapping(source = "unit.nameUnit", target = "nameUnit"),
            @Mapping(source = "unit.favorite", target = "favorite"),
            @Mapping(source = "unit.city.cityName", target = "cityName"),
            @Mapping(source = "unit.region.regionName", target = "regionName"),
            @Mapping(source = "unit.city.arabicCityName", target = "arabicCityName"),
            @Mapping(source = "unit.region.regionArabicName", target = "regionArabicName"),
            @Mapping(source = "latForMapping", target = "latForMapping"),
            @Mapping(source = "longForMapping", target = "longForMapping"),
            @Mapping(source = "price", target = "price", defaultValue = "0"),
            @Mapping(source = "oldPrice", target = "oldPrice", defaultValue = "0"),
            @Mapping(source = "evaluation.name", target = "evaluationName"),
            @Mapping(source = "evaluation.arabicName", target = "evaluationArabicName"),
            @Mapping(source = "totalEvaluation", target = "totalEvaluation")
    })
     UnitDtoFavorite toUnitFavoriteDto(Unit unit);

    List<UnitDtoFavorite> toUnitFavoriteDtoList(List<Unit> units);

    // Define a method to extract file paths from ImageData entities

    // Define a method to extract the first file path from FileForUnit entities
    default String extractFirstFileImagePath(List<FileForUnit> fileForUnits) {
        if (fileForUnits == null || fileForUnits.isEmpty()) {
            return null; // or return a default value if preferred
        }
        return fileForUnits.get(0).getFileImageUrl();
    }

    default String extractFirstFileVideoPath(List<FileForUnit> fileForUnits) {
        if (fileForUnits == null || fileForUnits.isEmpty()) {
            return null; // or return a default value if preferred
        }
        return fileForUnits.get(0).getFileVideoUrl();
    }

}
