package com.AlTaraf.Booking.Entity.unit.featureForHalls;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "feature_halls")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeatureForHalls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feature_halls_id")
    private Long id;

    @Column(name = "FEATURE_NAME")
    private String name;

    @Column(name = "FEATURE_ARABIC_NAME")
    private String arabicName;

    public FeatureForHalls(Long id) {
        this.id = id;
    }
}
