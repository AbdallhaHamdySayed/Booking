package com.AlTaraf.Booking.Entity.Evaluation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "Evaluation")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVALUATION_ID")
    private Long id;
    @Column(name = "EVALUATION_NAME")
    private String name;
    @Column(name = "EVALUATION_ARABIC_NAME")
    private String arabicName;
    @Column(name = "SCORE")
    private int score;
}
