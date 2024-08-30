package com.AlTaraf.Booking.Entity.unit.feature;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="feature")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FEATURE_NAME")
    private String name;

    @Column(name = "FEATURE_ARABIC_NAME")
    private String arabicName;

    public Feature(Long id) {
        this.id = id;
    }
}
