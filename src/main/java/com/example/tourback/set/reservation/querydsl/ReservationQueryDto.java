package com.example.tourback.set.reservation.querydsl;

import com.example.tourback.set.reservation.Reservation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationQueryDto {

    private String bookingCode;
    private String productCode;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private Integer numberOfTravelers;
    private Integer totalCost;
    private String paymentType;
    private Reservation.PaymentStatus paymentStatus;
    private String name;
    private String phone;
    private LocalDateTime createAt;

    private String title;
}
