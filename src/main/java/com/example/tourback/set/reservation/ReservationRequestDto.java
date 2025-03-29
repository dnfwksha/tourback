package com.example.tourback.set.reservation;

import com.example.tourback.set.companion.CompanionDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ReservationRequestDto {

    @JsonProperty("reservation")
    private ReservationDto reservationDto;
    @JsonProperty("travelers")
    private List<CompanionDto> companionDtos;
}
