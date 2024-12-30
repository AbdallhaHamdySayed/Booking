package com.AlTaraf.Booking.database.repository;

import com.AlTaraf.Booking.database.entity.TotalTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalTransactionsRepository extends JpaRepository<TotalTransactions, Long> {

}
