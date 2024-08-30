package com.AlTaraf.Booking.Entity.unit.accommodationType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accommodation_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOMMODATION_TYPE_ID")
    private Long id;
    @Column(name = "ACCOMMODATION_NAME")
    private String name;
    @Column(name = "ACCOMMODATION_ARABIC_NAME")
    private String arabicName;

    public AccommodationType(Long id) {
        this.id = id;
    }
}
