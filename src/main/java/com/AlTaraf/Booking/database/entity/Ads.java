package com.AlTaraf.Booking.database.entity;


import com.AlTaraf.Booking.database.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Table(name = "ADS")
@Data
@AllArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "ADS_ID"))
})
public class Ads extends BaseEntity<Integer> {

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

    @Column(name = "DATE_ADS")
    private LocalDate dateAds;

    @Column(name = "UUID_ADS")
    private String uuidAds;

    public Ads() {
        this.statusUnit = new StatusUnit();
        this.statusUnit.setId(1L);
    }

    public void setFileForAds(FileForAds fileForAds) {
        this.fileForAds = fileForAds;
        fileForAds.setAds(this);
    }
}
