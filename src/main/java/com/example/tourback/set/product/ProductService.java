package com.example.tourback.set.product;

import com.example.tourback.set.product.image.ProductImage;
import com.example.tourback.set.product.image.ProductImageRepository;
import com.example.tourback.set.product.querydsl.ProductQueryDto;
import com.example.tourback.set.productDetail.ProductDetail;
import com.example.tourback.set.productDetail.ProductDetailRepository;
import com.example.tourback.set.productDetail.image.ProductDetailImage;
import com.example.tourback.set.productDetail.image.ProductDetailImageRepository;
import com.example.tourback.set.reservableDate.ReservableDate;
import com.example.tourback.set.reservableDate.ReservableDateRepository;
import com.example.tourback.set.schedule.ScheduleRepositoryRepository;
import com.example.tourback.set.schedule.Schedule;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.tourback.set.product.image.ProductImage.ImageType.THUMBNAIL;
import static com.example.tourback.set.productDetail.image.ProductDetailImage.ImageType.*;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductDetailImageRepository productDetailImageRepository;
    private final ScheduleRepositoryRepository scheduleRepository;
    private final ReservableDateRepository reservableDateRepository;
    private final String bucketName;
    private final String region;
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    public ProductService(ProductRepository productRepository,
                          ProductImageRepository productImageRepository,
                          ProductDetailRepository productDetailRepository,
                          ProductDetailImageRepository productDetailImageRepository,
                          ScheduleRepositoryRepository scheduleRepository, ReservableDateRepository reservableDateRepository,
                          @Value("${aws.s3.bucket}") String bucketName,
                          @Value("${aws.region}") String region,
                          S3Client s3Client,
                          S3Presigner s3Presigner) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.productDetailImageRepository = productDetailImageRepository;
        this.productDetailRepository = productDetailRepository;
        this.scheduleRepository = scheduleRepository;
        this.reservableDateRepository = reservableDateRepository;
        this.bucketName = bucketName;
        this.region = region;
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
    }

    @Transactional
    public void save(ProductDto productDto) {
        List<MultipartFile> images = productDto.getImages();
        List<MultipartFile> itineraries =productDto.getItinerary();
        List<MultipartFile> reservations = productDto.getReservation();;
        List<MultipartFile> busStops = productDto.getBusStop();
        List<MultipartFile> cancels = productDto.getCancellationPolicy();
        List<LocalDate> availableDates = productDto.getAvailableDates();

        long all = productRepository.count() + 1;
        String productCode = "KDT" + all;

        try {
            Product product = new Product();
            product.setProductCode(productCode);
            product.setTitle(productDto.getTitle());
            product.setSubTitle(productDto.getSubTitle());
            product.setPrice(productDto.getPrice());
            product.setCategory(productDto.getCategory());
            productRepository.save(product);

            ProductDetail productDetail = new ProductDetail();
            productDetail.setProductCode(productCode);
            productDetail.setDepartAt(productDto.getDepartAt());
            productDetail.setDepartureLocation(productDto.getDepartureLocation());
            productDetail.setArrivalLocation(productDto.getArrivalLocation());
            productDetail.setTransportationType(productDto.getTransportationType());
            productDetail.setIncludedServices(productDto.getIncludedServices());
            productDetail.setExcludedServices(productDto.getExcludedServices());
            productDetail.setItinerary(productDto.getItineraryText());
            productDetail.setRecommend(productDto.getRecommend());
            productDetailRepository.save(productDetail);

            Schedule schedule = new Schedule();
            schedule.setProductCode(productCode);
            schedule.setDepartureDate(productDto.getDepartureDate());
            schedule.setArrivalDate(productDto.getArrivalDate());
            schedule.setDomestic("Y");
            schedule.setPeriod(productDto.getPeriod());
            schedule.setNowParticipants(productDto.getNowParticipants());
            schedule.setMinParticipants(productDto.getMinParticipants());
            schedule.setMaxParticipants(productDto.getMaxParticipants());
            scheduleRepository.save(schedule);

            for (int i = 0; i < availableDates.size(); i++) {
                ReservableDate reservableDate = new ReservableDate();
                reservableDate.setProductCode(productCode);
                reservableDate.setAvailableDate(availableDates.get(i));
                reservableDateRepository.save(reservableDate);
            }

            for (int i = 0; i < images.size(); i++) {
                MultipartFile image = images.get(i);
                String originalFilename = image.getOriginalFilename();
                String imageFilename = System.currentTimeMillis() + "image_" + originalFilename;
                String imageUrl = uploadFileToS3(image, imageFilename);

                ProductImage productImage = new ProductImage();
                productImage.setImageUrl(imageUrl);
                productImage.setDisplayOrder(i + 1);
                productImage.setProductCode(productCode);
                productImage.setImageType(THUMBNAIL);
                productImageRepository.save(productImage);
            }

            for (int i = 0; i < itineraries.size(); i++) {
                MultipartFile itinerary = itineraries.get(i);
                String originalFilename = itinerary.getOriginalFilename();
                String itineraryFilename = System.currentTimeMillis() + "detail_" + originalFilename;
                String itineraryUrl = uploadFileToS3(itinerary, itineraryFilename);

                ProductDetailImage productDetailImage = new ProductDetailImage();
                productDetailImage.setImageUrl(itineraryUrl);
                productDetailImage.setDisplayOrder(i + 1);
                productDetailImage.setProductCode(productCode);
                productDetailImage.setImageType(ITINERARY);
                productDetailImageRepository.save(productDetailImage);
            }

            for (int i = 0; i < reservations.size(); i++) {
                MultipartFile reservation = reservations.get(i);
                String originalFilename = reservation.getOriginalFilename();
                String reservationFilename = System.currentTimeMillis() + "detail_" + originalFilename;
                String reservationUrl = uploadFileToS3(reservation, reservationFilename);

                ProductDetailImage productDetailImage = new ProductDetailImage();
                productDetailImage.setImageUrl(reservationUrl);
                productDetailImage.setDisplayOrder(i + 1);
                productDetailImage.setProductCode(productCode);
                productDetailImage.setImageType(RESERVATION);
                productDetailImageRepository.save(productDetailImage);
            }

            for (int i = 0; i < busStops.size(); i++) {
                MultipartFile busStop = busStops.get(i);
                String originalFilename = busStop.getOriginalFilename();
                String busStopFilename = System.currentTimeMillis() + "detail_" + originalFilename;
                String busStopUrl = uploadFileToS3(busStop, busStopFilename);

                ProductDetailImage productDetailImage = new ProductDetailImage();
                productDetailImage.setImageUrl(busStopUrl);
                productDetailImage.setDisplayOrder(i + 1);
                productDetailImage.setProductCode(productCode);
                productDetailImage.setImageType(BUSSTOP);
                productDetailImageRepository.save(productDetailImage);
            }

            for (int i = 0; i < cancels.size(); i++) {
                MultipartFile cancel = cancels.get(i);
                String originalFilename = cancel.getOriginalFilename();
                String cancelFilename = System.currentTimeMillis() + "detail_" + originalFilename;
                String cancelUrl = uploadFileToS3(cancel, cancelFilename);

                ProductDetailImage productDetailImage = new ProductDetailImage();
                productDetailImage.setImageUrl(cancelUrl);
                productDetailImage.setDisplayOrder(i + 1);
                productDetailImage.setProductCode(productCode);
                productDetailImage.setImageType(CANCEL);
                productDetailImageRepository.save(productDetailImage);
            }
        } catch (Exception e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }

    public String update(ProductDto productDto) {
//        var image = productDto.getImages();
//        var detail = productDto.getImagesDetail();
//        var itinerary = productDto.getItinerary();
//        var cancel = productDto.getCancellationPolicy();
//
//        String originalImageFilename = image.getOriginalFilename();
//        String originalDetailFilename = detail.getOriginalFilename();
//        String originalItineraryFilename = itinerary.getOriginalFilename();
//        String originalCancelFilename = cancel.getOriginalFilename();
//
//        String imageFilename = System.currentTimeMillis() + "image_" + originalImageFilename;
//        String detailFilename = System.currentTimeMillis() + "detail_" + originalDetailFilename;
//        String itineraryFilename = System.currentTimeMillis() + "itinerary_" + originalItineraryFilename;
//        String cancelFilename = System.currentTimeMillis() + "cancel_" + originalCancelFilename;
//
//        try {
//            // 이미지 파일 업로드
//            String imageUrl = uploadFileToS3(image, imageFilename);
//
//            // 상세 이미지 파일 업로드
//            String detailUrl = uploadFileToS3(detail, detailFilename);
//            String itineraryUrl = uploadFileToS3(itinerary, itineraryFilename);
//            String cancelUrl = uploadFileToS3(cancel, cancelFilename);
//
//            String productCode = productDto.getProductCode();
//            //출발일자 가져오기
//
//            // 파일 정보를 데이터베이스에 저장
//            var product = productRepository.findByProductCode(productCode);
//            if (product == null) {
//                return "정보를 가져오지 못 했습니다.";
//            } else {
//                product.setTitle(productDto.getTitle());
//                product.setSubTitle(productDto.getSubTitle());
//                product.setPrice(productDto.getPrice());
//                product.setPeriod(productDto.getPeriod());
//                product.setCategory(productDto.getCategory());
//                productRepository.save(product);
//            }
//
//            var productDetail = productDetailRepository.findByProductCode(productCode);
//            if (productDetail == null) {
//                return "상세정보를 가져오지 못 했습니다.";
//            } else {
//                productDetail.setDepartAt(productDto.getDepartAt());
//                productDetail.setDepartureLocation(productDto.getDepartureLocation());
//                productDetail.setArrivalLocation(productDto.getArrivalLocation());
//                productDetail.setTransportationType(productDto.getTransportationType());
//                productDetail.setIncludedServices(productDto.getIncludedServices());
//                productDetail.setExcludedServices(productDto.getExcludedServices());
//                productDetail.setItinerary(itineraryUrl);
//                productDetail.setCancellationPolicy(cancelUrl);
//                productDetail.setRecommend(productDto.getRecommend());
//                productDetailRepository.save(productDetail);
//            }
//
//            var schedule = scheduleRepository.findByProductCode(productCode);
//            if (schedule == null) {
//                return "스케줄을 가져오지 못 했습니다.";
//            } else {
//                schedule.setDepartureDate(productDto.getDepartureDate());
//                schedule.setArrivalDate(productDto.getArrivalDate());
//                schedule.setNowParticipants(productDto.getNowParticipants());
//                schedule.setMinParticipants(productDto.getMinParticipants());
//                schedule.setMaxParticipants(productDto.getMaxParticipants());
//                scheduleRepository.save(schedule);
//            }
//
//            return "수정 완료";
//        } catch (Exception e) {
//            throw new RuntimeException("파일 업로드 실패", e);
//        }
        return null;
    }

    private String uploadFileToS3(MultipartFile file, String fileName) throws IOException {
        // S3 업로드 요청 생성
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .cacheControl("max-age=0")
                .build();

        // 파일 업로드 실행
        s3Client.putObject(
                putObjectRequest,
                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );

        // 업로드된 파일의 URL 생성 및 반환
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileName);
    }

    public List<String> uploadMultipleFilesToS3(List<MultipartFile> files) throws IOException {
        List<String> uploadedFileUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            // 각 파일에 대해 고유한 파일명 생성 (UUID 사용)
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // 기존 업로드 메소드 호출하여 각 파일 업로드
            String fileUrl = uploadFileToS3(file, fileName);
            uploadedFileUrls.add(fileUrl);
        }

        return uploadedFileUrls; // 모든 업로드된 파일의 URL 목록 반환
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

//    public List<Popular> findAll() {
//        List<Popular> populars = popularRepository.findAll();
//
//        // 각 엔티티의 S3 URL 설정
//        populars.forEach(this::setS3Urls);
//
//        return populars;
//    }
//
//    // 이미지 URL 설정 메서드
//    private void setS3Urls(Product product) {
//        if (product.getImages() != null && !product.getImages().isEmpty()) {
//            product.setImages(generatePresignedUrl(product.getImages()));
//        }
//    }
//
//    // Presigned URL 생성 메서드
//    private String generatePresignedUrl(String objectKey) {
//        try {
//            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(objectKey)
//                    .build();
//
//            // URL 유효 시간 설정 (예: 1시간)
//            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
//                    .signatureDuration(Duration.ofHours(1))
//                    .getObjectRequest(getObjectRequest)
//                    .build();
//
//            // Presigned URL 생성
//            PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
//            return presignedRequest.url().toString();
//
//        } catch (Exception e) {
//            // 로깅 또는 예외 처리
//            System.err.println("S3 URL 생성 중 오류 발생: " + e.getMessage());
//            return null;
//        }
//    }
//
//    // 만약 Presigned URL이 아닌 공개 URL이 필요하다면 아래 메서드 사용
//    private String generatePublicUrl(String objectKey) {
//        return String.format("https://%s.s3.%s.amazonaws.com/%s",
//                bucketName, region, objectKey);
//    }

    public ProductQueryDto getProductWithProductDetail(String productCode) {
        System.out.println(productCode);
        return productRepository.getQslProductWithProductDetail(productCode);
    }

    public void viewCount(String productCode) {
        ProductDetail productDetail = productDetailRepository.findByProductCode(productCode);
        if (productDetail != null) {
            productDetail.setViewCount(productDetail.getViewCount() + 1);
            productDetailRepository.save(productDetail);
        }
    }

    public ProductQueryDto reserve(String productCode) {
        return productRepository.reserve(productCode);
    }

    public List<ProductQueryDto> getProductWithProductImage() {
        return productRepository.getProductWithProductImage();
    }


    /* save할 때 리팩토링 하려고 했는데 update 할 때,
    productCode를 별도로 필요치 않아서 만들다가 말았다.
     추후 필요하다면 사용할 예정
     일단은 사용하지 않기로 결정함.
     */
    public void createProduct(ProductDto productDto,
                              Product product,
                              String productCode) {
        product.setProductCode(productCode);
        product.setTitle(productDto.getTitle());
        product.setSubTitle(productDto.getSubTitle());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());
        productRepository.save(product);
    }

    public void createProductDetail(ProductDto productDto,
                                    ProductDetail productDetail,
                                    String productCode,
                                    String itineraryUrl,
                                    String cancelUrl) {
        productDetail.setProductCode(productCode);
        productDetail.setDepartAt(productDto.getDepartAt());
        productDetail.setDepartureLocation(productDto.getDepartureLocation());
        productDetail.setArrivalLocation(productDto.getArrivalLocation());
        productDetail.setTransportationType(productDto.getTransportationType());
        productDetail.setIncludedServices(productDto.getIncludedServices());
        productDetail.setExcludedServices(productDto.getExcludedServices());
        productDetail.setItinerary(itineraryUrl);
        productDetail.setRecommend(productDto.getRecommend());
        productDetailRepository.save(productDetail);
    }

    public void createSchedule(ProductDto productDto,
                               Schedule schedule,
                               String productCode) {
        schedule.setProductCode(productCode);
        schedule.setDepartureDate(productDto.getDepartureDate());
        schedule.setArrivalDate(productDto.getArrivalDate());
        schedule.setNowParticipants(productDto.getNowParticipants());
        schedule.setMinParticipants(productDto.getMinParticipants());
        schedule.setMaxParticipants(productDto.getMaxParticipants());
        scheduleRepository.save(schedule);
    }




    /*
     * 여기까지 구현하다 말았다. 리팩토링 시, 사용 예정*/

}
