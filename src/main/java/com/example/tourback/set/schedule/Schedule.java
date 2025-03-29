package com.example.tourback.set.schedule;

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
public class Schedule extends BaseEntity {
    @Column(unique = true)
    private String productCode;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private int nowParticipants;
    private int minParticipants;
    private int maxParticipants;
    private int period;
    private String domestic="Y";
//    private String availableDate;
}
