package com.AlTaraf.Booking.Entity.unit.typesOfEventHalls;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "types_event_halls")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypesOfEventHalls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TYPES_EVENT_HALLS_ID")
    private Long id;
    @Column(name = "TYPES_EVENT_HALLS_NAME")
    private String name;
    @Column(name = "TYPES_EVENT_HALLS_ARABIC_NAME")
    private String arabicName;

    public TypesOfEventHalls(Long id) {
        this.id = id;
    }
}
