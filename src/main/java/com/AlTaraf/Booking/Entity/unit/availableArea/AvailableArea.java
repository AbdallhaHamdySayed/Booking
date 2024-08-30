package com.AlTaraf.Booking.Entity.unit.availableArea;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "available_area")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AVAILABLE_AREA_ID")
    private Long id;

    @Column(name = "AVAILABLE_AREA_NAME")
    private String name;

    @Column(name = "AVAILABLE_AREA_NAME_ARABIC")
    private String arabicName;

    public AvailableArea(Long id) {
        this.id = id;
    }
}
