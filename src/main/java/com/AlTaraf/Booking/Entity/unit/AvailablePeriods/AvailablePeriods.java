package com.AlTaraf.Booking.Entity.unit.AvailablePeriods;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "available_periods")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AvailablePeriods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "available_periods_id")
    private Long id;

    @Column(name = "AVAILABLE_PERIODS_NAME")
    private String name;

    @Column(name = "AVAILABLE_PERIODS_ARABIC_NAME")
    private String arabicName;

    public AvailablePeriods(Long id) {
        this.id = id;
    }
}
