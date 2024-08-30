package com.AlTaraf.Booking.Entity.Reservation;



import com.AlTaraf.Booking.Entity.Evaluation.Evaluation;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.common.Auditable;
import com.AlTaraf.Booking.Entity.unit.AvailablePeriods.AvailablePeriods;
import com.AlTaraf.Booking.Entity.unit.Unit;
import com.AlTaraf.Booking.Entity.unit.availableArea.AvailableArea;
import com.AlTaraf.Booking.Entity.unit.feature.Feature;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.Entity.unit.statusUnit.StatusUnit;
import com.AlTaraf.Booking.Entity.unit.subFeature.SubFeature;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "Reservation")
@Setter
@Getter
@AllArgsConstructor
public class Reservations extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private Long id;

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


//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "reservation_room_available",
//            joinColumns = @JoinColumn(name = "RESERVATION_ID"),
//            inverseJoinColumns = @JoinColumn(name = "ROOM_AVAILABLE_ID")
//    )
//    private Set<RoomAvailable> roomAvailableSet = new HashSet<>();
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "reservation_available_area",
//            joinColumns = @JoinColumn(name = "RESERVATION_ID"),
//            inverseJoinColumns = @JoinColumn(name = "AVAILABLE_AREA_ID"))
//    private Set<AvailableArea> availableAreaSet = new HashSet<>();

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

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "reservation_Room_Details",
//            joinColumns = @JoinColumn(name = "RESERVATION_ID"),
//            inverseJoinColumns = @JoinColumn(name = "ROOM_DETAILS_ID"))
//    private Set<RoomDetails> roomDetailsSet = new HashSet<>();

    @Column(name = "ADULTS_ALLOWED")
    private int adultsAllowed;

    @Column(name = "CHILDREN_ALLOWED")
    private int childrenAllowed;

    private int price;

    @Column(name = "DATE_OF_ARRIVAL")
    private LocalDate dateOfArrival;

    @Column(name = "DATE_OF_DEPARTURE")
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
