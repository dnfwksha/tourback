package com.example.tourback.set.product;

import com.example.tourback.global.s3Service.S3Upload;
import com.example.tourback.set.product.image.ProductImage;
import com.example.tourback.set.product.image.ProductImageRepository;
import com.example.tourback.set.product.querydsl.ProductQueryDto;
import com.example.tourback.set.productDetail.ProductDetail;
import com.example.tourback.set.productDetail.ProductDetailRepository;
import com.example.tourback.set.productDetail.image.ProductDetailImage;
import com.example.tourback.set.productDetail.image.ProductDetailImageRepository;
import com.example.tourback.set.reservableDate.ReservableDate;
import com.example.tourback.set.reservableDate.ReservableDateRepository;
import com.example.tourback.set.schedule.Schedule;
import com.example.tourback.set.schedule.ScheduleRepositoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

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
    private final S3Upload s3Upload;


    public ProductService(
            ProductRepository productRepository,
            ProductImageRepository productImageRepository,
            ProductDetailRepository productDetailRepository,
            ProductDetailImageRepository productDetailImageRepository,
            ScheduleRepositoryRepository scheduleRepository, ReservableDateRepository reservableDateRepository, S3Upload s3Upload
    ) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.productDetailImageRepository = productDetailImageRepository;
        this.productDetailRepository = productDetailRepository;
        this.scheduleRepository = scheduleRepository;
        this.reservableDateRepository = reservableDateRepository;
        this.s3Upload = s3Upload;
    }

    @Transactional
    public void save(ProductDto productDto) {
        List<MultipartFile> images = productDto.getImages();
        List<MultipartFile> itineraries = productDto.getItinerary();
        List<MultipartFile> reservations = productDto.getReservation();
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
            product.setRecommend(productDto.getRecommend());
            productRepository.save(product);

            ProductDetail productDetail = new ProductDetail();
            productDetail.setProductCode(productCode);
            productDetail.setDepartAt(productDto.getDepartAt());
            productDetail.setDepartureLocation(productDto.getDepartureLocation());
            productDetail.setArrivalLocation(productDto.getArrivalLocation());
            productDetail.setTransportationType(productDto.getTransportationType());
            productDetail.setIncludedServices(productDto.getIncludedServices());
            productDetail.setExcludedServices(productDto.getExcludedServices());
            productDetail.setItineraryText(productDto.getItineraryText());
            productDetailRepository.save(productDetail);

            Schedule schedule = new Schedule();
            schedule.setProductCode(productCode);
//            schedule.setDepartureDate(productDto.getDepartureDate());
//            schedule.setArrivalDate(productDto.getArrivalDate());
            schedule.setDomestic("Y");
            schedule.setPeriod(productDto.getPeriod());
            schedule.setNowParticipants(productDto.getNowParticipants());
            schedule.setMinParticipants(productDto.getMinParticipants());
            schedule.setMaxParticipants(productDto.getMaxParticipants());
            scheduleRepository.save(schedule);

            if (productDto.getAvailableDates() != null) {
                for (int i = 0; i < availableDates.size(); i++) {
                    ReservableDate reservableDate = new ReservableDate();
                    reservableDate.setProductCode(productCode);
                    reservableDate.setAvailableDate(availableDates.get(i));
                    reservableDateRepository.save(reservableDate);
                }
            }

            if (productDto.getImages() != null) {
                for (int i = 0; i < images.size(); i++) {
                    MultipartFile image = images.get(i);
                    String originalFilename = image.getOriginalFilename();
                    String imageFilename = System.currentTimeMillis() + "image_" + originalFilename;
                    String imageUrl = s3Upload.uploadFileToS3(image, imageFilename);

                    ProductImage productImage = new ProductImage();
                    productImage.setImageUrl(imageUrl);
                    productImage.setDisplayOrder(i + 1);
                    productImage.setProductCode(productCode);
                    productImage.setImageType(THUMBNAIL);
                    productImageRepository.save(productImage);
                }
            }

            if (productDto.getItinerary() != null) {
                for (int i = 0; i < itineraries.size(); i++) {
                    MultipartFile itinerary = itineraries.get(i);
                    String originalFilename = itinerary.getOriginalFilename();
                    String itineraryFilename = System.currentTimeMillis() + "detail_" + originalFilename;
                    String itineraryUrl = s3Upload.uploadFileToS3(itinerary, itineraryFilename);

                    ProductDetailImage productDetailImage = new ProductDetailImage();
                    productDetailImage.setImageUrl(itineraryUrl);
                    productDetailImage.setDisplayOrder(i + 1);
                    productDetailImage.setProductCode(productCode);
                    productDetailImage.setImageType(ITINERARY);
                    productDetailImageRepository.save(productDetailImage);
                }
            }

            if (productDto.getReservation() != null) {
                for (int i = 0; i < reservations.size(); i++) {
                    MultipartFile reservation = reservations.get(i);
                    String originalFilename = reservation.getOriginalFilename();
                    String reservationFilename = System.currentTimeMillis() + "detail_" + originalFilename;
                    String reservationUrl = s3Upload.uploadFileToS3(reservation, reservationFilename);

                    ProductDetailImage productDetailImage = new ProductDetailImage();
                    productDetailImage.setImageUrl(reservationUrl);
                    productDetailImage.setDisplayOrder(i + 1);
                    productDetailImage.setProductCode(productCode);
                    productDetailImage.setImageType(RESERVATION);
                    productDetailImageRepository.save(productDetailImage);
                }
            }

            if (productDto.getBusStop() != null) {
                for (int i = 0; i < busStops.size(); i++) {
                    MultipartFile busStop = busStops.get(i);
                    String originalFilename = busStop.getOriginalFilename();
                    String busStopFilename = System.currentTimeMillis() + "detail_" + originalFilename;
                    String busStopUrl = s3Upload.uploadFileToS3(busStop, busStopFilename);

                    ProductDetailImage productDetailImage = new ProductDetailImage();
                    productDetailImage.setImageUrl(busStopUrl);
                    productDetailImage.setDisplayOrder(i + 1);
                    productDetailImage.setProductCode(productCode);
                    productDetailImage.setImageType(BUSSTOP);
                    productDetailImageRepository.save(productDetailImage);
                }
            }

            if (productDto.getCancellationPolicy() != null) {
                for (int i = 0; i < cancels.size(); i++) {
                    MultipartFile cancel = cancels.get(i);
                    String originalFilename = cancel.getOriginalFilename();
                    String cancelFilename = System.currentTimeMillis() + "detail_" + originalFilename;
                    String cancelUrl = s3Upload.uploadFileToS3(cancel, cancelFilename);

                    ProductDetailImage productDetailImage = new ProductDetailImage();
                    productDetailImage.setImageUrl(cancelUrl);
                    productDetailImage.setDisplayOrder(i + 1);
                    productDetailImage.setProductCode(productCode);
                    productDetailImage.setImageType(CANCEL);
                    productDetailImageRepository.save(productDetailImage);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }

    @Transactional
    public String update(ProductDto productDto) {


//        String productCode = productDto.getProductCode();
//
//        try {
//            Product product = productRepository.findByProductCode(productCode);
//            product.setProductCode(productCode);
//            product.setTitle(productDto.getTitle());
//            product.setSubTitle(productDto.getSubTitle());
//            product.setPrice(productDto.getPrice());
//            product.setCategory(productDto.getCategory());
//            product.setRecommend(productDto.getRecommend());
//            productRepository.save(product);
//
//            ProductDetail productDetail = productDetailRepository.findByProductCode(productCode);
//            productDetail.setProductCode(productCode);
//            productDetail.setDepartAt(productDto.getDepartAt());
//            productDetail.setDepartureLocation(productDto.getDepartureLocation());
//            productDetail.setArrivalLocation(productDto.getArrivalLocation());
//            productDetail.setTransportationType(productDto.getTransportationType());
//            productDetail.setIncludedServices(productDto.getIncludedServices());
//            productDetail.setExcludedServices(productDto.getExcludedServices());
//            productDetail.setItineraryText(productDto.getItineraryText());
//            productDetailRepository.save(productDetail);
//
//            Schedule schedule = scheduleRepository.findByProductCode(productCode);
//            schedule.setProductCode(productCode);
////            schedule.setDepartureDate(productDto.getDepartureDate());
////            schedule.setArrivalDate(productDto.getArrivalDate());
//            schedule.setDomestic("Y");
//            schedule.setPeriod(productDto.getPeriod());
//            schedule.setNowParticipants(productDto.getNowParticipants());
//            schedule.setMinParticipants(productDto.getMinParticipants());
//            schedule.setMaxParticipants(productDto.getMaxParticipants());
//            scheduleRepository.save(schedule);
//
//            List<LocalDate> availableDates = productDto.getAvailableDates();
//            if (availableDates != null) {
//                for (int i = 0; i < availableDates.size(); i++) {
//                    reservableDateRepository.deleteByProductCode(productCode);
//                    ReservableDate reservableDate = new ReservableDate();
//                    reservableDate.setProductCode(productCode);
//                    reservableDate.setAvailableDate(availableDates.get(i));
//                    reservableDateRepository.save(reservableDate);
//                }
//            }
//            List<MultipartFile> newImages = productDto.getImages();
//            List<MultipartFile> itineraries = productDto.getItinerary();
//            List<MultipartFile> reservations = productDto.getReservation();
//            List<MultipartFile> busStops = productDto.getBusStop();
//            List<MultipartFile> cancels = productDto.getCancellationPolicy();
//
//
//            if (newImages != null && !newImages.isEmpty()) {
//                List<ProductImage> existingImages = productImageRepository.findByProductCode(productCode);
//                if (existingImages.size() > newImages.size()) {
//                    for (int i = existingImages.size(); i < newImages.size(); i++) {
//                        reservableDateRepository.delete(existingImages.get(i));
//                    }
//                }
//
//                for (int i = 0; i < newImages.size(); i++) {
//                    var aa= newImages.get(i);
//
//                    if(i<existingImages.size()){
//                        var existingImage = existingImages.get(i);
//                        existingImage.setImageUrl();
//                        existingImage.setDisplayOrder(i+1);
//                        productImageRepository.save(existingImage);
//                    }













//                    MultipartFile image = newImages.get(i);
//                    String originalFilename = image.getOriginalFilename();
//                    String imageFilename = System.currentTimeMillis() + "image_" + originalFilename;
//                    String imageUrl = s3Upload.uploadFileToS3(image, imageFilename);
//
//                    productImage.setImageUrl(imageUrl);
//                    productImage.setDisplayOrder(i + 1);
//                    productImage.setProductCode(productCode);
//                    productImage.setImageType(THUMBNAIL);
//                    productImageRepository.save(productImage);
//                }
//            }

//            if (productDto.getItinerary() != null) {
//                for (int i = 0; i < itineraries.size(); i++) {
//                    MultipartFile itinerary = itineraries.get(i);
//                    String originalFilename = itinerary.getOriginalFilename();
//                    String itineraryFilename = System.currentTimeMillis() + "detail_" + originalFilename;
//                    String itineraryUrl = s3Upload.uploadFileToS3(itinerary, itineraryFilename);
//
//                    List<ProductDetailImage> productDetailImage = productDetailImageRepository.findByProductCode(productCode);
//                    productDetailImage.setImageUrl(itineraryUrl);
//                    productDetailImage.setDisplayOrder(i + 1);
//                    productDetailImage.setProductCode(productCode);
//                    productDetailImage.setImageType(ITINERARY);
//                    productDetailImageRepository.save(productDetailImage);
//                }
//            }
//
//            if (productDto.getReservation() != null) {
//                for (int i = 0; i < reservations.size(); i++) {
//                    MultipartFile reservation = reservations.get(i);
//                    String originalFilename = reservation.getOriginalFilename();
//                    String reservationFilename = System.currentTimeMillis() + "detail_" + originalFilename;
//                    String reservationUrl = s3Upload.uploadFileToS3(reservation, reservationFilename);
//
//                    List<ProductDetailImage> productDetailImage = productDetailImageRepository.findByProductCode(productCode);
//                    productDetailImage.setImageUrl(reservationUrl);
//                    productDetailImage.setDisplayOrder(i + 1);
//                    productDetailImage.setProductCode(productCode);
//                    productDetailImage.setImageType(RESERVATION);
//                    productDetailImageRepository.save(productDetailImage);
//                }
//            }
//
//            if (productDto.getBusStop() != null) {
//                for (int i = 0; i < busStops.size(); i++) {
//                    MultipartFile busStop = busStops.get(i);
//                    String originalFilename = busStop.getOriginalFilename();
//                    String busStopFilename = System.currentTimeMillis() + "detail_" + originalFilename;
//                    String busStopUrl = s3Upload.uploadFileToS3(busStop, busStopFilename);
//
//                    List<ProductDetailImage> productDetailImage = productDetailImageRepository.findByProductCode(productCode);
//                    productDetailImage.setImageUrl(busStopUrl);
//                    productDetailImage.setDisplayOrder(i + 1);
//                    productDetailImage.setProductCode(productCode);
//                    productDetailImage.setImageType(BUSSTOP);
//                    productDetailImageRepository.save(productDetailImage);
//                }
//            }
//
//            if (productDto.getCancellationPolicy() != null) {
//                for (int i = 0; i < cancels.size(); i++) {
//                    MultipartFile cancel = cancels.get(i);
//                    String originalFilename = cancel.getOriginalFilename();
//                    String cancelFilename = System.currentTimeMillis() + "detail_" + originalFilename;
//                    String cancelUrl = s3Upload.uploadFileToS3(cancel, cancelFilename);
//
//                    List<ProductDetailImage> productDetailImage = productDetailImageRepository.findByProductCode(productCode);
//                    productDetailImage.setImageUrl(cancelUrl);
//                    productDetailImage.setDisplayOrder(i + 1);
//                    productDetailImage.setProductCode(productCode);
//                    productDetailImage.setImageType(CANCEL);
//                    productDetailImageRepository.save(productDetailImage);
//                }
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("파일 업로드 실패", e);
//        }

        return null;
    }


    public List<Product> findAll() {
        return productRepository.findAll();
    }

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

    @Transactional
    public void delete(String productCode) {
        productRepository.deleteByProductCode(productCode);
        productDetailRepository.deleteByProductCode(productCode);
        scheduleRepository.deleteByProductCode(productCode);
        productImageRepository.deleteByProductCode(productCode);
        productDetailImageRepository.deleteByProductCode(productCode);

    }
}
