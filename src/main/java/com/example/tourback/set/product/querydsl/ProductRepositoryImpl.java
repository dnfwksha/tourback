package com.example.tourback.set.product.querydsl;

import com.example.tourback.set.product.Product;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.example.tourback.set.product.QProduct.product;
import static com.example.tourback.set.product.image.QProductImage.productImage;
import static com.example.tourback.set.productDetail.QProductDetail.productDetail;
import static com.example.tourback.set.productDetail.image.QProductDetailImage.productDetailImage;
import static com.example.tourback.set.reservableDate.QReservableDate.reservableDate;
import static com.example.tourback.set.schedule.QSchedule.schedule;
import static com.querydsl.jpa.JPAExpressions.select;

@Slf4j
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /*
    관리자 페이지 넣을 때
    상세페이지 나올 때
    사용
    */
    @Override
    public ProductQueryDto getQslProductWithProductDetail(String productCode) {
        ProductQueryDto result = jpaQueryFactory
                .select(Projections.bean(ProductQueryDto.class,
                        product.productCode,
                        product.title,
                        product.subTitle,
                        product.price,
                        product.category,
                        product.recommend,
                        productDetail.departAt,
                        productDetail.departureLocation,
                        productDetail.arrivalLocation,
                        productDetail.transportationType,
                        productDetail.accommodationInfo,
                        productDetail.includedServices,
                        productDetail.excludedServices,
                        productDetail.itineraryText,
                        schedule.period,
                        schedule.domestic,
                        schedule.departureDate,
                        schedule.arrivalDate,
                        schedule.nowParticipants,
                        schedule.minParticipants,
                        schedule.maxParticipants,
                        product.status
                ))
                .from(product)
                .leftJoin(productDetail)
                .on(product.productCode.eq(productDetail.productCode))
                .leftJoin(schedule)
                .on(product.productCode.eq(schedule.productCode))
                .where(product.productCode.eq(productCode))
                .fetchOne();

        List<ProductQueryDto.ImageDto> productImages = jpaQueryFactory
                .select(Projections.bean(ProductQueryDto.ImageDto.class,
                        productImage.imageUrl,
                        productImage.displayOrder,
                        productImage.imageType
                ))
                .from(productImage)
                .where(productImage.productCode.eq(productCode))
                .fetch();

        log.info("상품 코드: {}, 이미지 개수: {}", productCode, productImages.size());
        // 상세 이미지 쿼리
        List<ProductQueryDto.DetailImageDto> detailImages = jpaQueryFactory
                .select(Projections.bean(ProductQueryDto.DetailImageDto.class,
                        productDetailImage.imageUrl,
                        productDetailImage.displayOrder,
                        productDetailImage.imageType
                ))
                .from(productDetailImage)
                .where(productDetailImage.productCode.eq(productCode))
                .fetch();
        log.info("상품 코드: {}, 상세 이미지 개수: {}", productCode, detailImages.size());

        List<ProductQueryDto.ReservableDataDto> availableDates = jpaQueryFactory
                .select(Projections.bean(ProductQueryDto.ReservableDataDto.class,
                        reservableDate.availableDate
                ))
                .from(reservableDate)
                .where(reservableDate.productCode.eq(productCode))
                .fetch();
        log.info("상품 코드: {}, available 개수: {}", productCode, availableDates.size());

        result.setProductImages(productImages);
        result.setDetailImages(detailImages);
        result.setAvailableDates(availableDates);

        return result;
    }

    @Override
    public List<ProductQueryDto> getProductWithProductImage() {
        List<String> productCodes = jpaQueryFactory
                .select(product.productCode)
                .from(product)
                .fetch();

        List<ProductQueryDto> result = new ArrayList<>();

        for (String productCode : productCodes) {
            ProductQueryDto productQueryDto = jpaQueryFactory
                    .select(Projections.bean(ProductQueryDto.class,
                            product.productCode,
                            product.title,
                            product.subTitle,
                            product.price
                    ))
                    .from(product)
                    .where(product.productCode.eq(productCode))
                    .fetchOne();

            List<ProductQueryDto.ImageDto> productImages = jpaQueryFactory
                    .select(Projections.bean(ProductQueryDto.ImageDto.class,
                            productImage.imageType,
                            productImage.imageUrl,
                            productImage.displayOrder
                    ))
                    .from(productImage)
                    .where(productImage.productCode.eq(productCode))
                    .fetch();

            productQueryDto.setProductImages(productImages);
            result.add(productQueryDto);
        }
        return result;
    }

    @Override
    public ProductQueryDto reserve(String productCode) {
        return jpaQueryFactory
                .select(Projections.bean(ProductQueryDto.class,
                        product.title,
                        product.price
                ))
                .from(product)
                .where(product.productCode.eq(productCode))
                .fetchOne();
    }


}