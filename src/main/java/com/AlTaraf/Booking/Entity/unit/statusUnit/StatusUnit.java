package com.AlTaraf.Booking.Entity.unit.statusUnit;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "status_unit")
@Data
public class StatusUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATUS_ID")
    private Long id;

    @Column(name = "STATUS_NAME")
    private String name;

    @Column(name = "STATUS_ARABIC_NAME")
    private String arabicName;

    public StatusUnit() {
    }

    public StatusUnit(Long id, String name, String arabicName) {
        this.id = id;
        this.name = name;
        this.arabicName = arabicName;
    }

}
