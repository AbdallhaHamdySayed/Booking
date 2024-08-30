package com.AlTaraf.Booking.Repository.payment;

import com.AlTaraf.Booking.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface PayemntRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE p.custom_ref = :customRef")
    Payment findByCustomRef(@Param("customRef") String customRef);

    @Modifying
    @Transactional
    @Query("DELETE FROM Payment p WHERE p.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

}
