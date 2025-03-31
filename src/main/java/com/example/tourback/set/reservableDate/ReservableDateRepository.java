package com.example.tourback.set.reservableDate;

import com.example.tourback.set.product.image.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservableDateRepository extends JpaRepository<ReservableDate,Long> {
    List<ReservableDate> findByProductCode(String productCode);

//    void deleteByProductCode(String productCode);

//    void delete(ProductImage productImage);
}
