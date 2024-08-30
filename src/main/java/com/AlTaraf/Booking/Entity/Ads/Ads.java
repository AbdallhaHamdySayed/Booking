package com.AlTaraf.Booking.Entity.Ads;

import com.AlTaraf.Booking.Entity.File.FileForAds;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Table(name = "ADS")
@Data
@AllArgsConstructor
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADS_ID")
    private Long id;

    @OneToOne(mappedBy = "ads", cascade = CascadeType.ALL,  orphanRemoval = true)
    @JoinColumn(name = "FILE_FOR_ADS_ID")
    private FileForAds fileForAds;

    @OneToOne
    @JoinColumn(name = "UNIT_ID")
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "STATUS_ID")
    private StatusUnit statusUnit;

    public Ads() {
        this.statusUnit = new StatusUnit();
        this.statusUnit.setId(1L);
    }

    public void setFileForAds(FileForAds fileForAds) {
        this.fileForAds = fileForAds;
        fileForAds.setAds(this);
    }
}
