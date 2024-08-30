package com.AlTaraf.Booking.Entity.unit.unitType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unit_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UNIT_TYPE_ID")
    private Long id;

    @Column(name = "TYPE_NAME")
    private String name;

    @Column(name = "TYPE_ARABIC_NAME")
    private String arabicName;

    public UnitType(Long id) {
        this.id = id;
    }
}