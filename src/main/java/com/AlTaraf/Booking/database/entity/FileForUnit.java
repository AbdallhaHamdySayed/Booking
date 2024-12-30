package com.AlTaraf.Booking.database.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "FILE_FOR_UNIT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FileForUnit {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private String type;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    private String fileImageUrl;

    private String fileVideoUrl;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "UNIT_ID")
    @JsonBackReference
    private Unit unit;

    private LocalDate createdDate;

    private LocalTime createdTime;

    public FileForUnit(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
        this.createdTime = LocalTime.now();
    }
}
