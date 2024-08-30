package com.AlTaraf.Booking.Entity.unit.hotelClassification;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hotel_classification")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelClassification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HOTEL_CLASSIFICATION_ID")
    private Long id;

    @Column(name = "HOTEL_CLASSIFICATION_NAME")
    private String name;

    @Column(name = "HOTEL_CLASSIFICATION_ARABIC_NAME")
    private String arabicName;

    public HotelClassification(Long id) {
        this.id = id;
    }
}
