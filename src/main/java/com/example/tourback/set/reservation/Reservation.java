package com.example.tourback.set.reservation;

import com.example.tourback.global.baseEntity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String productCode;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    @Column(name = "number_of_travelers")
    private int numberOfTravelers;
    private int totalCost;
    private String paymentType;
    private String specialRequests;
    private String name;
    private String phone;
    private String boardingLocation;
    private String note;
}
