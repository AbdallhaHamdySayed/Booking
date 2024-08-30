package com.AlTaraf.Booking.Repository.Transactions;

import com.AlTaraf.Booking.Entity.Transactions.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

}