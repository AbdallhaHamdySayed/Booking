package com.AlTaraf.Booking.service;

import com.AlTaraf.Booking.database.entity.City;
import com.AlTaraf.Booking.database.entity.Region;
import com.AlTaraf.Booking.database.repository.CityRepository;
import com.AlTaraf.Booking.database.repository.RegionRepository;
import com.AlTaraf.Booking.rest.dto.RegionDto;
import com.AlTaraf.Booking.rest.mapper.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegionService {
    @Autowired
    RegionRepository regionRepository;

    @Autowired
    CityRepository cityRepository;

    public List<RegionDto> getRegionsByCityId(Long cityId) {
        List<Region> regions = regionRepository.findByCityId(cityId);

        return regions.stream()
                .map(RegionMapper.INSTANCE::entityToDto)
                .collect(Collectors.toList());
    }

    public void deleteRegion(Long regionId) {
        regionRepository.deleteById(regionId);
    }

    public RegionDto addRegionToCity(Long cityId, RegionDto regionDto) {
        Optional<City> optionalCity = cityRepository.findById(cityId);
        if (optionalCity.isPresent()) {
            City city = optionalCity.get();
            Region region = new Region();
            region.setRegionName(regionDto.getRegionName());
            region.setRegionArabicName(regionDto.getRegionArabicName());
            region.setCity(city);
            regionRepository.save(region);

            cityRepository.save(city);

            return new RegionDto(region);
        } else {
            throw new RuntimeException("City not found with id: " + cityId);
        }
    }
}
