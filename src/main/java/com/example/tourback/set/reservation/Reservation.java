package com.example.tourback.set.reservation;

import com.example.tourback.global.baseEntity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
public class Reservation extends BaseEntity {

    @Column(unique = true)
    private String bookingCode;

    private String username;

    private String productCode;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private int numberOfTravelers;
    private int totalCost;
    private String paymentType;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    public enum PaymentStatus {
        PAID,UNPAID
    }

    private String name;
    private String phone;
    private String boardingLocation;
    private String note;
}
