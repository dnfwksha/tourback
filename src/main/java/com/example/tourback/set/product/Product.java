package com.example.tourback.set.product;

import com.example.tourback.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@Entity
public class Product extends BaseEntity {
    @Column(unique = true)
    private String productCode;
    private String title;
    private String subTitle;
    private int price;
    private String category;
    private String recommend;

    @Builder.Default
    private String status="Y";
}
