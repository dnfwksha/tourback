package com.example.tourback.set.productDetail.image;

import com.example.tourback.set.product.image.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDetailImageRepository extends JpaRepository<ProductDetailImage,Long> {
    List<ProductDetailImage> findByProductCode(String productCode);
    void deleteByProductCode(String productCode);
}
