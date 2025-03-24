package com.AlTaraf.Booking.database.entity;

import com.AlTaraf.Booking.database.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Table(name = "ERROR_LOG")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "ERROR_LOG_ID"))
})
@Data
public class ErrorLog extends BaseEntity<Integer> {

    @Column(name = "ERROR_DATE")
    private LocalDateTime errorDate;

    @Column(name = "LOGIN_USER")
    private String loginUser;

    @Column(name = "REQUESTED_PAYLOAD")
    private String requestedPayload;

    @Column(name = "EXCEPTION_TRACE")
    private String exceptionTrace;

}
