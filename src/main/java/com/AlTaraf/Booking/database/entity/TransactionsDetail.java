package com.AlTaraf.Booking.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TRANSACTIONS_DETAIL")
public class TransactionsDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CUSTOM_REF")
    private String customRef;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "Date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "TRANSACTION_ID")
    private Transactions transactions;

    @Column(name = "GATEWAY_ENGLISH_NAME")
    private String gatewayEnglishName;

    @Column(name = "GATEWAY_ARABIC_NAME")
    private String gatewayArabicName;

    @Column(name = "VALUE")
    private Double value;
}
