package com.AlTaraf.Booking.Mapper.Unit;


import com.AlTaraf.Booking.Dto.Image.FileForUnitDTO;
import com.AlTaraf.Booking.Dto.Unit.FeatureForHalls.FeatureForHallsDto;
import com.AlTaraf.Booking.Dto.Unit.availablePeriodsHalls.AvailablePeriodsDto;
import com.AlTaraf.Booking.Entity.File.FileForUnit;
import com.AlTaraf.Booking.Entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.featureForHalls.FeatureForHalls;
import com.AlTaraf.Booking.Payload.response.Unit.EventHallsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EventHallsMapper {
    @Mapping(source = "id", target = "unitId")
    @Mapping(source = "typesOfEventHalls", target = "typesOfEventHalls")
    @Mapping(target = "imagePaths", expression = "java(extractFileImagePaths(unit.getFileForUnits()))")
    @Mapping(target = "videoPaths", expression = "java(extractFirstFileVideoPath(unit.getFileForUnits()))")
    @Mapping(source = "capacityHalls", target = "capacityHalls")
    @Mapping(source = "nameUnit", target = "nameUnit")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "city", target = "cityDto")
    @Mapping(source = "region", target = "regionDto")
    @Mapping(source = "featuresHallsSet", target = "featuresHallsDto", qualifiedByName = "mapEntityToFeaturesHallsDto")
    @Mapping(source = "availablePeriodsHallsSet", target = "availablePeriodsHallsDto", qualifiedByName = "mapEntityToAvailablePeriodsHallsDto")
//    @Mapping(source = "oldPriceHall", target = "oldPriceHall")
//    @Mapping(source = "newPriceHall", target = "newPriceHall")
    @Mapping(source = "latForMapping", target = "latForMapping")
    @Mapping(source = "longForMapping", target = "longForMapping")
    @Mapping(source = "price", target = "price", defaultValue = "0")
    @Mapping(source = "oldPrice", target = "oldPrice", defaultValue = "0")
    @Mapping(source = "evaluation.name", target = "evaluationName")
    @Mapping(source = "evaluation.arabicName", target = "evaluationArabicName")
    @Mapping(source = "dateOfArrival", target = "dateOfArrival", qualifiedByName = "formatDate")
    @Mapping(source = "departureDate", target = "departureDate", qualifiedByName = "formatDate")
    EventHallsResponse toEventHallsResponse(Unit unit);

    @Named("mapEntityToFeaturesHallsDto")
    default Set<FeatureForHallsDto> mapEntityToFeaturesHallsDto(Set<FeatureForHalls> featuresHallsSet) {
        return featuresHallsSet.stream()
                .map(feature -> new FeatureForHallsDto(feature.getId(), feature.getName(), feature.getArabicName())) // Assuming FeatureForHallsDto has constructor taking id and name
                .collect(Collectors.toSet());
    }

    @Named("mapEntityToAvailablePeriodsHallsDto")
    default Set<AvailablePeriodsDto> mapEntityToAvailablePeriodsHallsDto(Set<AvailablePeriods> availablePeriodsHallsSet) {
        return availablePeriodsHallsSet.stream()
                .map(period -> new AvailablePeriodsDto(period.getId(), period.getName(), period.getArabicName())) // Assuming AvailablePeriodsDto has constructor taking id and name
                .collect(Collectors.toSet());
    }

    List<EventHallsResponse> toEventHallsList(List<Unit> unitList);

    default List<FileForUnitDTO> mapImages(List<FileForUnit> fileForUnits) {
        return fileForUnits.stream()
                .map(this::mapToImageDataDTO)
                .collect(Collectors.toList());
    }

    default FileForUnitDTO mapToImageDataDTO(FileForUnit fileForUnit) {
        FileForUnitDTO imageDataDTO = new FileForUnitDTO();
        imageDataDTO.setName(fileForUnit.getName());
        imageDataDTO.setFileImageUrl(fileForUnit.getFileImageUrl());
        imageDataDTO.setFileVideoUrl(fileForUnit.getFileVideoUrl());
        return imageDataDTO;
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