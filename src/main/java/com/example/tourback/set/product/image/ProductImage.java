package com.example.tourback.set.product.image;

import com.example.tourback.global.baseEntity.ImageBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class ProductImage extends ImageBaseEntity {

    // 해당 테이블의 키 값인 ProductCode로 조인할 예정
    private String productCode;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;
    public enum ImageType {
        THUMBNAIL
    }
}
