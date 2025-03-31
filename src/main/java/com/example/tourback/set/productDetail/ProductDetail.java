package com.example.tourback.set.productDetail;

import com.example.tourback.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
@Entity
public class ProductDetail extends BaseEntity {
    @Column(unique = true)
    private String productCode;
    private String departAt;
    private int viewCount;
    private String departureLocation;
    private String arrivalLocation;
    private String transportationType;
    @Builder.Default
    private String accommodationInfo="N";
    private String includedServices;
    private String excludedServices;
    private String itineraryText;
}
