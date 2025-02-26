package com.AlTaraf.Booking.rest.mapper;

import com.AlTaraf.Booking.database.entity.FileForUnit;
import com.AlTaraf.Booking.database.entity.Unit;
import com.AlTaraf.Booking.rest.dto.FileForUnitDTO;
import com.AlTaraf.Booking.rest.dto.UnitResidenciesResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UnitResidenciesResponseMapper {
    @Mapping(source = "id", target = "unitId")
    @Mapping(source = "unitType.id", target = "unitTypeId")
    @Mapping(target = "imagePaths", expression = "java(extractFileImagePaths(unit.getFileForUnits()))")
    @Mapping(target = "videoPaths", expression = "java(extractFirstFileVideoPath(unit.getFileForUnits()))")
    @Mapping(source = "nameUnit", target = "nameUnit")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "city", target = "cityDto")
    @Mapping(source = "region", target = "regionDto")
    @Mapping(source = "accommodationType", target = "accommodationType")
    @Mapping(source = "hotelClassification", target = "hotelClassification")
    @Mapping(source = "typeOfApartment", target = "typeOfApartment")
    @Mapping(source = "roomAvailableSet", target = "roomAvailables")
    @Mapping(source = "basicFeaturesSet", target = "features")
    @Mapping(source = "subFeaturesSet", target = "subFeatures")
    @Mapping(source = "availableAreaSet", target = "availableAreas")
    @Mapping(source = "latForMapping", target = "latForMapping")
    @Mapping(source = "longForMapping", target = "longForMapping")
    @Mapping(source = "evaluation.name", target = "evaluationName")
    @Mapping(source = "evaluation.arabicName", target = "evaluationArabicName")
    @Mapping(source = "dateOfArrival", target = "dateOfArrival", qualifiedByName = "formatDate")
    @Mapping(source = "departureDate", target = "departureDate", qualifiedByName = "formatDate")
    UnitResidenciesResponseDto toResponseDto(Unit unit);

    default List<FileForUnitDTO> mapImages(List<FileForUnit> files) {
        return files.stream()
                .map(this::mapToFileForUnitDTO)
                .collect(Collectors.toList());
    }

    default FileForUnitDTO mapToFileForUnitDTO(FileForUnit fileForUnit) {
        FileForUnitDTO fileForUnitDTO = new FileForUnitDTO();
        fileForUnitDTO.setName(fileForUnit.getName());
        fileForUnitDTO.setFileImageUrl(fileForUnit.getFileImageUrl());
        fileForUnitDTO.setFileVideoUrl(fileForUnit.getFileVideoUrl());
        return fileForUnitDTO;
    }

    default List<String> extractFileImagePaths(List<FileForUnit> fileForUnits) {
        if (fileForUnits == null) {
            return Collections.emptyList();
        }
        return fileForUnits.stream()
                .sorted(Comparator.comparing(FileForUnit::getCreatedDate)
                        .thenComparing(FileForUnit::getCreatedTime))
                .map(FileForUnit::getFileImageUrl)
                .collect(Collectors.toList());
    }

    default String extractFirstFileVideoPath(List<FileForUnit> fileForUnits) {
        if (fileForUnits == null || fileForUnits.isEmpty()) {
            return null; // or return a default value if preferred
        }
        return fileForUnits.get(0).getFileVideoUrl();
    }

    @Named("formatDate")
    static String formatDate(LocalDate date) {
        return date != null ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }
}
