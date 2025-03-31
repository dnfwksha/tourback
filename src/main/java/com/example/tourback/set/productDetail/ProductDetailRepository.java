package com.example.tourback.set.productDetail;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail,Long> {
    boolean existsByProductCode(String productCode);
    ProductDetail findByProductCode(String productCode);

    void deleteByProductCode(String productCode);
}
