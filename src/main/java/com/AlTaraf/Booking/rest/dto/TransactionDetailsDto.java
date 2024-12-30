package com.AlTaraf.Booking.rest.dto;

import com.AlTaraf.Booking.database.entity.Transactions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailsDto {

    private Long id;

    private Long userId;

    private String phone;

    private Date date;

    private Transactions transactions;

    private String gatewayEnglishName;

    private String gatewayArabicName;

    private String customRef;

    private Double value;


}
