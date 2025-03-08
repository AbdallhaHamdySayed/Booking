package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.IntegrationsUrls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntegrationsUrlRepository extends JpaRepository<IntegrationsUrls, Integer> {

}
