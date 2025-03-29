package com.example.tourback.set.product.querydsl;

import com.example.tourback.set.product.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    ProductQueryDto getQslProductWithProductDetail(String productCode);

    List<ProductQueryDto> getProductWithProductImage();

    ProductQueryDto reserve(String productCode);
}
