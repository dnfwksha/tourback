package com.example.tourback.set.reservation;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReservationDto {
    // ReservationDto
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private int numberOfTravelers;
    private int totalCost;
    private String paymentType;
    private String reservationName;
    private String reservationPhone;
    private String reservationBoardingLocation;
    private String note;
    private String productCode;
}
