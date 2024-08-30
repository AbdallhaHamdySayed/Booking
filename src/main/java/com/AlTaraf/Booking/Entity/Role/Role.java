package com.AlTaraf.Booking.Entity.Role;

import com.AlTaraf.Booking.Entity.enums.ERole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role", nullable = false)
    private ERole name;

    private String arabicName;

    private String englishName;
}