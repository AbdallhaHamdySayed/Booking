package com.AlTaraf.Booking.Entity.unit.subFeature;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sub_feature")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SUB_FEATURE_NAME")
    private String name;

    @Column(name = "SUB_FEATURE_ARABIC_NAME")
    private String arabicName;

    public SubFeature(Long id) {
        this.id = id;
    }
}
