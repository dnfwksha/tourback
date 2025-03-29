package com.example.tourback.set.product;

import com.example.tourback.set.product.querydsl.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    Product findByProductCode(String productCode);
}
