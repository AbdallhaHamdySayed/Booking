package com.AlTaraf.Booking.Dto.Transactions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalTransactionsDto {
    private Long totalTransactions;
    private Long totalReservationsTransactions;
    private Long totalSubscriptionsTransactions;
}
