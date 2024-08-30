package com.AlTaraf.Booking.Entity.Ads;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PACKAGE_ADS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageAds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PACKAGE_ADS_ID")
    private Long id;

    @Column(name = "ADS_NAME")
    private String name;

    @Column(name = "ADS_ARABIC_NAME")
    private String arabicName;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "NUMBER_ADS")
    private Integer numberAds;
}
