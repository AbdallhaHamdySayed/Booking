package com.AlTaraf.Booking.Service.cityAndRegion;

import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.Entity.cityAndregion.City;
import com.AlTaraf.Booking.Entity.cityAndregion.Region;
import com.AlTaraf.Booking.Mapper.RegionMapper;
import com.AlTaraf.Booking.Repository.cityAndregion.CityRepository;
import com.AlTaraf.Booking.Repository.cityAndregion.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<RegionDto> getRegionsByCityId(Long cityId) {
        List<Region> regions = regionRepository.findByCityId(cityId);

        // Use the mapper to convert entities to DTOs
        return regions.stream()
                .map(RegionMapper.INSTANCE::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRegion(Long regionId) {
        regionRepository.deleteById(regionId);
    }

    @Override
    public RegionDto addRegionToCity(Long cityId, RegionDto regionDto) {
        Optional<City> optionalCity = cityRepository.findById(cityId);
        if (optionalCity.isPresent()) {
            City city = optionalCity.get();
            Region region = new Region();
            region.setRegionName(regionDto.getRegionName());
            region.setRegionArabicName(regionDto.getRegionArabicName());
            region.setCity(city);
            regionRepository.save(region);

//            city.getRegions().add(region);
            cityRepository.save(city);

            return new RegionDto(region);
        } else {
            throw new RuntimeException("City not found with id: " + cityId);
        }
    }
}
