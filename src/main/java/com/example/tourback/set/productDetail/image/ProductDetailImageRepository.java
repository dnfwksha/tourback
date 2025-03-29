package com.example.tourback.set.productDetail.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDetailImageRepository extends JpaRepository<ProductDetailImage,Long> {
    List<ProductDetailImage> findByProductCodeOrderByDisplayOrder(String productCode);
}
