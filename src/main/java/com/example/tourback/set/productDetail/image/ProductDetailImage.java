package com.example.tourback.set.productDetail.image;

import com.example.tourback.global.baseEntity.ImageBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class ProductDetailImage extends ImageBaseEntity {

    // 해당 테이블의 키 값인 ProductCode로 조인할 예정
    private String productCode;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;
    public enum ImageType {
        ITINERARY, RESERVATION, BUSSTOP, CANCEL
    }
}