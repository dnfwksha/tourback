package com.example.tourback.set.product.image;

import com.example.tourback.set.reservableDate.ReservableDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
    List<ProductImage> findByProductCode(String productCode);
    void deleteByProductCode(String productCode);
}
