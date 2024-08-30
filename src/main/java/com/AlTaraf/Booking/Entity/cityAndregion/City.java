package com.AlTaraf.Booking.Entity.cityAndregion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    private String cityName;  // Renamed the property to avoid conflict with the entity name

    @Column(name = "arabic_name")
    private String arabicCityName;

    public City(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Region> regions = new ArrayList<>();
//
//    @OneToMany(mappedBy = "city")  // Refers to the 'city' property in the User entity
//    @JsonIgnore
//    private Set<User> users = new HashSet<>();


}
