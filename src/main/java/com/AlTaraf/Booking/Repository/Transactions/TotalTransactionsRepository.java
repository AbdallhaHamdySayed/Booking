package com.AlTaraf.Booking.Repository.Transactions;

import com.AlTaraf.Booking.Entity.Transactions.TotalTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalTransactionsRepository extends JpaRepository<TotalTransactions, Long> {

}
