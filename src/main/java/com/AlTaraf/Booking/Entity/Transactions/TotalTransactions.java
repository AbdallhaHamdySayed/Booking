package com.AlTaraf.Booking.Entity.Transactions;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TOTAL_TRANSACTIONS")
public class TotalTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TOTAL_TRANSACTIONS")
    private Long totalTransactions;

    @Column(name = "TOTAL_RESERVATIONS_TRANSACTIONS")
    private Long totalReservationsTransactions;

    @Column(name = "TOTAL_SUBSCRIPTIONS_TRANSACTIONS")
    private Long totalSubscriptionsTransactions;

}
