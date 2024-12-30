package com.AlTaraf.Booking.database.entity;

import com.AlTaraf.Booking.database.entity.common.Auditable;
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
@Table(name = "technical_support_units")
@Entity
public class TechnicalSupportForUnits extends Auditable<String> {
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
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "UNIT_ID")
    private Unit unit;

    @Transient
    private Duration elapsedTime;

    @PostLoad
    private void calculateElapsedTime() {
        this.elapsedTime = Duration.between(
                LocalDateTime.ofInstant(this.createdDate.toInstant(), ZoneId.systemDefault()),
                LocalDateTime.now()
        );
    }

    public TechnicalSupportForUnits() {
        this.seen = false;
    }
}
