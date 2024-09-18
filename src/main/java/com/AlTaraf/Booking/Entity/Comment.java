package com.AlTaraf.Booking.Entity;


import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.unit.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
