package com.example.tourback.set.product.querydsl;

import com.example.tourback.set.product.image.ProductImage;
import com.example.tourback.set.productDetail.image.ProductDetailImage;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductQueryDto {
    // Product 정보
    private String productCode;
    private String title;
    private String subTitle;
    private int price;
    private int period;
    private String category;
    private String images;
    private String domestic;
    private String status;

    // ProductDetail 정보
    private String departAt;
    private String imagesDetail;
    private String departureLocation;
    private String arrivalLocation;
    private String transportationType;
    private String accommodationInfo;
    private String includedServices;
    private String excludedServices;
    private String itineraryText;
    private String cancellationPolicy;
    private String recommend;

    // Schedule 정보
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private int nowParticipants;
    private int minParticipants;
    private int maxParticipants;

    //  ReservableData 정보
    List<ReservableDataDto> availableDates;
    @Getter
    @Setter
    @NoArgsConstructor
    public static class ReservableDataDto {
        private LocalDate availableDate;
    }


    // ProductImage 정보
    private List<ImageDto> productImages;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ImageDto {
        private String imageUrl;
        private int displayOrder;
        private ProductImage.ImageType imageType;
    }


    // ProductDetailImage 정보
    private List<DetailImageDto> detailImages;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class DetailImageDto {
        private String imageUrl;
        private int displayOrder;
        private ProductDetailImage.ImageType imageType;
    }
}
