package com.AlTaraf.Booking.Repository.cityAndregion;

import com.AlTaraf.Booking.Entity.cityAndregion.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    List<Region> findByCityId(Long cityId);
    Optional<Region> findByIdAndCityId(Long regionId, Long cityId);

}
