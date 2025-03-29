package com.example.tourback.set.schedule.querydsl;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ScheduleQueryDto {

    private LocalDate departureDate;
}
