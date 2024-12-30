package com.AlTaraf.Booking.service;



import com.AlTaraf.Booking.database.entity.City;
import com.AlTaraf.Booking.database.entity.Region;
import com.AlTaraf.Booking.database.repository.CityRepository;
import com.AlTaraf.Booking.database.repository.RegionRepository;
import com.AlTaraf.Booking.rest.dto.CityDto;
import com.AlTaraf.Booking.rest.dto.RegionDto;
import com.AlTaraf.Booking.rest.mapper.CityMapper;
import com.AlTaraf.Booking.rest.mapper.RegionMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    @Autowired
    CityRepository cityRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    CityMapper cityMapper;

    public CityDto createCity(CityDto cityDto) {
        City city = new City();
        city.setCityName(cityDto.getCityName());
        city.setArabicCityName(cityDto.getArabicCityName());

        List<Region> regions = new ArrayList<>();
        for (RegionDto regionDto : cityDto.getRegions()) {
            Region region = new Region();
            region.setRegionName(regionDto.getRegionName());
            region.setRegionArabicName(regionDto.getRegionArabicName());
            region.setCity(city);
            regions.add(region);
        }

        city.setRegions(regions);

        City savedCity = cityRepository.save(city);

        return new CityDto(savedCity);
    }


    @Transactional
    public Region updateRegionInCity(Long cityId, Long regionId, RegionDto RegionDto) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundException("City not found with id: " + cityId));

        Region region = regionRepository.findByIdAndCityId(regionId, cityId)
                .orElseThrow(() -> new EntityNotFoundException("Region not found with id: " + regionId));

        Region updatedRegion = RegionMapper.INSTANCE.updateDtoToEntity(RegionDto, region);

        return regionRepository.save(updatedRegion);
    }

    public List<CityDto> getAllCities() {
        List<City> cities = cityRepository.findAll();
        return cityMapper.citiesToCityDTOs(cities);
    }

    public void deleteCity(Long cityId) {
        cityRepository.deleteById(cityId);
    }

    public void save(City city) {
        cityRepository.save(city);
    }

    public Optional<City> findById(Long id) {
        return cityRepository.findById(id);
    }


}

