package com.AlTaraf.Booking.Entity.Wallet;

import com.AlTaraf.Booking.Entity.User.User;
import com.AlTaraf.Booking.Entity.common.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "WALLET")
public class Wallet extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WALLET_ID")
    private Long id;

    @Column(name = "PROCESS_NAME")
    private String processName;

    @Column(name = "PROCESS_ENGLISH_NAME")
    private String processEnglishName;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    private User user;

    @Column(name = "VALUE")
    private Double value;

    @Column(name = "NAME_UNIT")
    private String nameUnit;

    @Column(name = "PACKAGE_NAME")
    private String packageName;

    @Column(name = "PAYMENT_GATE")
    private String paymentGate;

    @Column(name = "FLAG")
    private Boolean flag;

    public Wallet(String processName,
                  String processEnglishName,
                  Double value ,
                  User user,
                  String nameUnit,
                  String packageName,
                  String paymentGate,
                  Boolean flag) {
        this.processName = processName;
        this.processEnglishName = processEnglishName;
        this.value = value;
        this.user = user;
        this.nameUnit = nameUnit;
        this.packageName = packageName;
        this.paymentGate = paymentGate;
        this.flag = flag;
    }
}
