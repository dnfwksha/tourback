package com.example.tourback.set.product;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class ProductDto {
    private String productCode;
    @NotBlank(message = "상품 이름을 입력해주세요.")
    private String title;
    @NotBlank(message = "상품 부제목을 입력해주세요.")
    private String subTitle;
    @Min(0)
    private int price;
    @Min(0)
    private int period;
    @NotBlank(message = "카테고리를 입력해주세요.")
    private String category;
    @NotBlank(message = "승차지점을 입력해주세요.")
    private String departAt;
    @Future
    private LocalDate departureDate;
    @Future
    private LocalDate arrivalDate;
    @Min(20)
    @Max(45)
    private int maxParticipants;
    @Min(20)
    @Max(45)
    private int minParticipants;
    private int nowParticipants;
    @NotBlank(message = "이동수단을 입력해주세요.")
    private String transportationType;
    @NotBlank(message = "추천 여부를 입력해주세요.")
    private String recommend;
    @NotBlank(message = "출발지역을 입력해주세요.")
    private String departureLocation;
    @NotBlank(message = "도착지역을 입력해주세요.")
    private String arrivalLocation;
    @NotBlank(message = "포함된 서비스를 입력해주세요.")
    private String includedServices;
    @NotBlank(message = "포함된 서비스를 입력해주세요.")
    private String excludedServices;
    @NotBlank(message = "포함된 서비스를 입력해주세요.")
    private String itineraryText;

    private List<LocalDate> availableDates;
//    @NotNull(message = "상품 썸네일을 입력해주세요.")
    private List<MultipartFile> images;
//    @NotNull(message = "여행 일정을 입력해주세요.")
    private List<MultipartFile> itinerary;
//    @NotNull(message = "상품 상페세이지를 입력해주세요.")
    private List<MultipartFile> reservation;
//    @NotNull(message = "버스 승강장을 입력해주세요.")
    private List<MultipartFile> busStop;
//    @NotNull(message = "상품 취소 규정을 입력해주세요.")
    private List<MultipartFile> cancellationPolicy;
}


//List<MultipartFile> images = productDto.getImages();
//List<MultipartFile> itineraries =productDto.getItinerary();
//List<MultipartFile> reservations = productDto.getReservation();;
//List<MultipartFile> busStops = productDto.getBusStop();
//List<MultipartFile> cancels = productDto.getCancellationPolicy();