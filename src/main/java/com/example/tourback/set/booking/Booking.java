package com.example.tourback.set.booking;

import com.example.tourback.global.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Booking extends BaseEntity {
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private Member member;
//
//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;
//
//    @ManyToOne
//    @JoinColumn(name = "schedule_id")
//    private Schedule schedule;

    private int persons;
    private int totalPrice;
    private String status;
}
