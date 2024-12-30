package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.TechnicalSupport;
import com.AlTaraf.Booking.database.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicalSupportRepository extends JpaRepository<TechnicalSupport, Long> {
    void deleteByUser(User user);

    Page<TechnicalSupport> findBySeen(boolean seen, Pageable pageable);

}
