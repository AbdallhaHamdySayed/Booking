package com.AlTaraf.Booking.Service.cityAndRegion;

import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.CityDto;
import com.AlTaraf.Booking.Dto.cityDtoAndRoleDto.RegionDto;
import com.AlTaraf.Booking.Entity.cityAndregion.City;
import com.AlTaraf.Booking.Entity.cityAndregion.Region;
import com.AlTaraf.Booking.Mapper.city.CityMapper;
import com.AlTaraf.Booking.Mapper.RegionMapper;
import com.AlTaraf.Booking.Repository.cityAndregion.CityRepository;
import com.AlTaraf.Booking.Repository.cityAndregion.RegionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    CityMapper cityMapper;

    @Override
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

    @Override
    public List<CityDto> getAllCities() {
        List<City> cities = cityRepository.findAll();
        return cityMapper.citiesToCityDTOs(cities);
    }

    @Override
    public void deleteCity(Long cityId) {
        cityRepository.deleteById(cityId);
    }

    @Override
    public void save(City city) {
        cityRepository.save(city);
    }

    @Override
    public Optional<City> findById(Long id) {
        return cityRepository.findById(id);
    }

}