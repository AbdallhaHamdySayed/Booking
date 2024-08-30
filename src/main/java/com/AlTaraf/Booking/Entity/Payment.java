package com.AlTaraf.Booking.Entity;

import com.AlTaraf.Booking.Entity.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.UUID;

@Entity
@Table(name = "PAYMENT")
@Data
@AllArgsConstructor
public class Payment {

    @Id
    @NotNull
    @Column(name = "Custom_Ref")
    private String custom_ref;

    @Column(name = "ID")
    private String id;

    @Column(name = "Amount")
    private Double amount;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Email")
    private String email;

    @Column(name = "Backend_Url")
    private String backend_url;

    private String payment_method;

    private String our_ref;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    private Boolean isActive;

    public Payment() {
        this.id = "LE4B3xwrXBNWDEGL5PYVAKbmQgrz6xvjGNZjed7y2M0JaRko9nwl14O3qbQ2n6zN";
        this.custom_ref = UUID.randomUUID().toString();
        this.our_ref = "El Taraf";
        this.backend_url = "/response-payment";
    }

}
