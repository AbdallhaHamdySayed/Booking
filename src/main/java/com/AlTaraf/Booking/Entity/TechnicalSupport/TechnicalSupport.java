package com.AlTaraf.Booking.Entity.TechnicalSupport;

import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.common.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@Table(name = "technical_support")
@Entity
public class TechnicalSupport extends Auditable<String>  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    @Email
    private String email;

    @Column
    private String message;

    @Column
    private Boolean seen;


    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = true)
    private User user;

    @Transient
    private Duration elapsedTime;

    @PostLoad
    private void calculateElapsedTime() {
        this.elapsedTime = Duration.between(
                LocalDateTime.ofInstant(this.createdDate.toInstant(), ZoneId.systemDefault()),
                LocalDateTime.now()
        );
    }

    public TechnicalSupport() {
        this.seen = false;
    }
}
