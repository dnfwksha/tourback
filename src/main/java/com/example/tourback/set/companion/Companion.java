package com.example.tourback.set.companion;

import com.example.tourback.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
public class Companion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numberOfPeople;
    private String bookingCode;

    private String name;
    private String phone;
    private String boardingLocation;
}
