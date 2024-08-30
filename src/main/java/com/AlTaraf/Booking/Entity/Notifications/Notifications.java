package com.AlTaraf.Booking.Entity.Notifications;

import com.AlTaraf.Booking.Entity.Role.Role;
import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.common.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "NOTIFICATIONS")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Notifications extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String body;
    @ManyToOne
    private User user;

    private String logoUrl;

    @ManyToOne
    private Role role;

    @Column
    private Boolean seen;

    private String lang;

    @Transient
    private Duration elapsedTime;

    @PostLoad
    private void calculateElapsedTime() {
        this.elapsedTime = Duration.between(
                LocalDateTime.ofInstant(this.createdDate.toInstant(), ZoneId.systemDefault()),
                LocalDateTime.now()
        );
    }

    public Notifications() {
        this.logoUrl = "https://play.min.io/ehgzly/logo.jpg";
    }
}