package com.AlTaraf.Booking.Entity.Transactions;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TRANSACTIONS")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ARABIC_NAME")
    private String arabicName;

    @Column(name = "ENGLISH_NAME")
    private String englishName;
}
