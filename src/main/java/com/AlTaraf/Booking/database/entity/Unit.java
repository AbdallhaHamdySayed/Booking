package com.AlTaraf.Booking.database.entity;

import com.AlTaraf.Booking.database.entity.common.Auditable;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "unit")
@Getter
@Setter
@AllArgsConstructor
public class Unit extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "UNIT_TYPE_ID", nullable = false)
    private UnitType unitType;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ACCOMMODATION_TYPE_ID")
    private AccommodationType accommodationType;

    @ManyToOne
    @JoinColumn(name = "TYPES_EVENT_HALLS_ID")
    private TypesOfEventHalls typesOfEventHalls;

    @Column(name = "NAME_UNIT")
    private String nameUnit;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "CITY_ID" , nullable = false)
    private City city;

    @ManyToOne
    @JoinColumn(name = "REGION_ID", nullable = false)
    private Region region;

    @ManyToOne
    @JoinColumn(name = "HOTEL_CLASSIFICATION_ID")
    private HotelClassification hotelClassification;

    @ManyToOne
    @JoinColumn(name = "TYPES_APARTMENT_ID")
    private TypeOfApartment typeOfApartment;

    // الغرف المتاحة فنادق بداية
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "unit_room_available",
            joinColumns = @JoinColumn(name = "UNIT_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROOM_AVAILABLE_ID")
    )
    private Set<RoomAvailable> roomAvailableSet = new HashSet<>();
    // الغرف المتاحة فنادق نهاية

    // الغرف المتاحة لي الشقق الفندقية و الشقق الخارجية و المنتجعات بداية
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "unit_available_area",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "AVAILABLE_AREA_ID"))
    private Set<AvailableArea> availableAreaSet = new HashSet<>();
    // الغرف المتاحة لي الشقق الفندقية و الشقق الخارجية و المنتجعات نهاية

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "unit_basic_features",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id"))
    private Set<Feature> basicFeaturesSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "unit_sub_features",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_feature_id"))
    private Set<SubFeature> subFeaturesSet = new HashSet<>();

    // الغرف المتاحة فنادق نهاية

    @Column(name = "FAVORITE")
    private Boolean favorite;

    @ManyToOne
    @JoinColumn(name = "STATUS_ID")
    private StatusUnit statusUnit;

    // قاعات المناسبات البداية
    private Integer capacityHalls = 0;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "features_unit_halls",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_halls_id"))
    private Set<FeatureForHalls> featuresHallsSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "available_periods_unit_halls",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "available_periods_id"))
    private Set<AvailablePeriods> availablePeriodsHallsSet = new HashSet<>();

    private Integer oldPriceHall = 0;

    private Integer newPriceHall = 0;

    private Integer ChaletNewPrice = 0;

    private Integer ChaletOldPrice = 0;

    private Integer apartmentNewPrice = 0;

    private Integer apartmentOldPrice = 0;

    private Integer loungeOldPrice = 0;

    private Integer loungeNewPrice = 0;

    private Integer price = 0;

    private Integer oldPrice = 0;

    private Double commission = 20.0;

    private Double latForMapping;

    private Double longForMapping;

    // قاعات المناسبات النهاية

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RoomDetails> roomDetails;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RoomDetailsForAvailableArea> roomDetailsForAvailableAreaList;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<FileForUnit> fileForUnits;

    @Column(name = "DATE_OF_ARRIVAL")
    private LocalDate dateOfArrival;

    @Column(name = "DATE_OF_DEPARTURE")
    private LocalDate departureDate;

    @ManyToOne
    @JoinColumn(name = "EVALUATION_ID")
    private Evaluation evaluation;

    @Column(name = "ADULTS_ALLOWED")
    private Integer adultsAllowed = 0;

    @Column(name = "CHILDREN_ALLOWED")
    private Integer childrenAllowed =0 ;

    @Column(name = "TOTAL_EVALUATION")
    private Integer totalEvaluation = 0; // Total number of evaluations

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment> comments;

    @Column(name = "PeriodsCount")
    private Long periodCount;

    @Column(name = "YoutubeUrl")
    private String youtubeUrl;

    public Unit() {
        this.statusUnit = new StatusUnit();
        this.statusUnit.setId(1L);
        this.favorite = false;
        this.dateOfArrival = LocalDate.now(); // Set dateOfArrival to the current date
//        this.departureDate = LocalDate.now().plusDays(30); // Set departureDate to 30 days after dateOfArrival
        this.departureDate = LocalDate.now().plusYears(1);
    }


    public void setPrice(Integer price) {

        if (unitType != null && unitType.getId() == 2) {
            if (getNewPriceHall() == null || getNewPriceHall().intValue() == 0) {
                price = getOldPriceHall();
            } else {
                price = getNewPriceHall();
            }
        }

        else if (unitType != null && unitType.getId() == 1 && accommodationType != null) {

            if (accommodationType.getId() == 4) {
                if (getChaletNewPrice() == null || getChaletNewPrice().intValue() == 0) {
                    price = getChaletOldPrice();
                } else {
                    price = getChaletNewPrice();
                }
            }

            if (accommodationType.getId() == 3) {
                if (getApartmentNewPrice() == null || getApartmentNewPrice().intValue() == 0) {
                    price = getApartmentOldPrice();
                } else {
                    price = getApartmentNewPrice();
                }
            }

            else if (accommodationType.getId() == 6) {
                if (getLoungeNewPrice() == null || getLoungeNewPrice().intValue() == 0) {
                    price = getLoungeOldPrice();
                } else {
                    price = getLoungeNewPrice();
                }
            }
        }
        this.price = price;
    }

    public void setOldPrice(Integer oldPrice) {
        if (unitType != null && unitType.getId() == 2) {
            oldPrice = getOldPriceHall();
        }

        else if (unitType != null && unitType.getId() == 1 && accommodationType != null) {

            if (accommodationType.getId() == 4) {
                oldPrice = getChaletOldPrice();
            }

            if (accommodationType.getId() == 3) {
                oldPrice = getApartmentOldPrice();
            }

            else if (accommodationType.getId() == 6) {
                oldPrice = getLoungeOldPrice();
            }
        }

        this.oldPrice = oldPrice;
    }


    public void calculatePrice() {

        if (unitType != null && unitType.getId() == 2) {
            if (getNewPriceHall() == null || getNewPriceHall() == 0) {
                price = getOldPriceHall() != null ? getOldPriceHall() : 0; // Set a default value if oldPriceHall is null
            } else {
                price = getNewPriceHall();
            }
        }

        else if (unitType != null && unitType.getId() == 1 && accommodationType != null) {

            if (accommodationType.getId() == 3) {
                if (getApartmentNewPrice() == null || getApartmentNewPrice() == 0) {
                    price = getApartmentOldPrice() != null ? getApartmentOldPrice() : 0; // Set a default value if chaletOldPrice is null
                } else {
                    price = getApartmentNewPrice();
                }
            }

            else if (accommodationType.getId() == 4) {
                if (getChaletNewPrice() == null || getChaletNewPrice() == 0) {
                    price = getChaletOldPrice() != null ? getChaletOldPrice() : 0; // Set a default value if chaletOldPrice is null
                } else {
                    price = getChaletNewPrice();
                }
            }


            else if (accommodationType.getId() == 6) {
                if (getLoungeNewPrice() == null || getLoungeNewPrice() == 0) {
                    price = getLoungeOldPrice() != null ? getLoungeOldPrice() : 0; // Set a default value if resortOldPrice is null
                } else {
                    price = getLoungeNewPrice();
                }
            }
        }
    }

    public void calculateOldPrice() {

        if (unitType != null && unitType.getId() == 2) {
            oldPrice = getOldPriceHall() != null ? getOldPriceHall() : 0; // Set a default value if oldPriceHall is null

        }

        else if (unitType != null && unitType.getId() == 1 && accommodationType != null) {

            if (accommodationType.getId() == 3) {
                oldPrice = getApartmentOldPrice() != null ? getApartmentOldPrice() : 0; // Set a default value if chaletOldPrice is null

            }

            else if (accommodationType.getId() == 4) {
                oldPrice = getChaletOldPrice() != null ? getChaletOldPrice() : 0; // Set a default value if chaletOldPrice is null

            }

            else if (accommodationType.getId() == 6) {
                oldPrice = getLoungeOldPrice() != null ? getLoungeOldPrice() : 0; // Set a default value if resortOldPrice is null
            }
        }
    }



    public void incrementTotalEvaluation() {
        if (this.totalEvaluation == null) {
            this.totalEvaluation = 0;
        }
        this.totalEvaluation++;
    }

}
