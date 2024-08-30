package com.AlTaraf.Booking.Entity.unit.roomAvailable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "room_available")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomAvailable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROOM_AVAILABLE_ID")
    private Long id;

    @Column(name = "ROOM_AVAILABLE_NAME")
    private String name;

    @Column(name = "ROOM_AVAILABLE_NAME_ARABIC")
    private String arabicName;

    public RoomAvailable(Long id) {
        this.id = id;
    }

    //    @OneToMany(mappedBy = "roomAvailable", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<RoomDetails> roomDetailsList = new ArrayList<>();


//    @ElementCollection
//    @CollectionTable(name = "room_type_details", joinColumns = @JoinColumn(name = "ROOM_AVAILABLE_ID"))
//    private List<RoomTypeDetails> roomTypeDetailsList = new ArrayList<>();

//    @OneToMany(mappedBy = "roomAvailable", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<RoomDetails> roomDetailsList = new ArrayList<>();




}
