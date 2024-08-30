package com.AlTaraf.Booking.Repository.cityAndregion;

import com.AlTaraf.Booking.Entity.cityAndregion.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByCityName(String cityName);
    Optional<City> findByArabicCityName(String arabicCityName);

}

