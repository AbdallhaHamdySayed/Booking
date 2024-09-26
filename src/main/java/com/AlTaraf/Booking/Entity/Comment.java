package com.AlTaraf.Booking.Entity;


import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "COMMENTS")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    @Column(name = "CONTENT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "UNIT_ID")
    @JsonBackReference
    private Unit unit;

    private String phoneUser;

    private String userName;

    private String fileDownloadUri;

    private LocalDate createdDate;

    private LocalTime createdTime;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
        this.createdTime = LocalTime.now();
    }
}
