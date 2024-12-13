package com.AlTaraf.Booking.Entity;

import com.AlTaraf.Booking.Entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedule")
@Data
public class Schedule extends BaseEntity<Integer> {

    @Column(name = "SCHEDULE_START_DATE")
    private LocalDateTime startDate;

    @Column(name = "SCHEDULE_END_DATE")
    private LocalDateTime endDate;

    @Column(name = "SCHEDULE_OPERATION")
    private String operation;

    @Column(name = "SCHEDULE_UUID")
    private String uuid;

}
