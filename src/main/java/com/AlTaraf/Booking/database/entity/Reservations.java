package com.AlTaraf.Booking.database.entity;

import com.AlTaraf.Booking.database.entity.base.BaseAuditEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Reservation")
@Setter
@Getter
@AllArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "RESERVATION_ID"))
})
public class Reservations extends BaseAuditEntity<Integer> {

    @Column(name = "CLIENT_NAME")
    private String clientName;

    @Column(name = "CLIENT_PHONE")
    private String clientPhone;

    @ManyToOne
    @JoinColumn(name = "UNIT_ID")
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "EVALUATION_ID")
    private Evaluation evaluation;

    @ManyToOne
    @JoinColumn(name = "ROOM_AVAILABLE_ID")
    private RoomAvailable roomAvailable;

    @ManyToOne
    @JoinColumn(name = "AVAILABLE_AREA_ID")
    private AvailableArea availableArea;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reservation_basic_features",
            joinColumns = @JoinColumn(name = "RESERVATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "feature_id"))
    private Set<Feature> basicFeaturesSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reservation_sub_features",
            joinColumns = @JoinColumn(name = "RESERVATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "sub_feature_id"))
    private Set<SubFeature> subFeaturesSet = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "STATUS_ID")
    private StatusUnit statusUnit;

    private int capacityHalls;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reservation_periods_unit_halls",
            joinColumns = @JoinColumn(name = "RESERVATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "available_periods_id"))
    private Set<AvailablePeriods> availablePeriodsHallsSet = new HashSet<>();

    @Column(name = "ADULTS_ALLOWED")
    private int adultsAllowed;

    @Column(name = "CHILDREN_ALLOWED")
    private int childrenAllowed;

    private int price;

    @Column(name = "DATE_OF_ARRIVAL")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfArrival;

    @Column(name = "DATE_OF_DEPARTURE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;

    @Column(name = "IS_EVALUATING")
    private Boolean isEvaluating;
    
    @Column(name = "COMMISION")
    private Double commision = 20.0;

    public Reservations() {
        this.statusUnit = new StatusUnit();
        this.statusUnit.setId(1L);
    }

}
