package com.AlTaraf.Booking.Entity.unit.typesOfApartments;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "types_apartment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeOfApartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TYPES_APARTMENT_ID")
    private Long id;

    @Column(name = "TYPES_APARTMENT_NAME")
    private String name;

    @Column(name = "TYPES_APARTMENT_ARABIC_NAME")
    private String arabicName;

    public TypeOfApartment(Long id) {
        this.id = id;
    }
}
