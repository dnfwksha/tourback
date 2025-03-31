package com.example.tourback.set.product;

import com.example.tourback.global.RsData;
import com.example.tourback.set.product.querydsl.ProductQueryDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> SavePop(@Valid ProductDto productDto) {
        ResponseEntity<?> validate = validateFiles(productDto);
        if (validate != null) return validate;

        productService.save(productDto);
        return ResponseEntity.ok(RsData.of(null, "여행지 등록 완료되었습니다.", null));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@Valid ProductDto productDto) {
        ResponseEntity<?> validate = validateFiles(productDto);
        if (validate != null) return validate;

        productService.update(productDto);
        return ResponseEntity.ok(RsData.of(null, "여행지 수정 완료되었습니다.", null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String productCode) {
        productService.delete(productCode);
        return ResponseEntity.ok(RsData.of(null, "여행지 삭제 완료되었습니다.", null));
    }

    @GetMapping("/all")
    public List<Product> findALl() {
        return productService.findAll();
    }

    @GetMapping("/main")
    public List<ProductQueryDto> getProductWithProductImage() {
        return productService.getProductWithProductImage();
//        return productService.findAll();
    }

    @PostMapping("/viewcount/{id}")
    public void viewCount(@PathVariable("id") String productCode) {
        productService.viewCount(productCode);
    }

    @PostMapping("/productdetail/{id}")     // 상품 상세보기 품목들 담아 옴
    public ProductQueryDto detail2(@PathVariable("id") String productCode) {
        return productService.getProductWithProductDetail(productCode);
    }

    @PostMapping("/reserve/{id}")     // 상품 예약페이지 데이터 담아 옴
    public ProductQueryDto reserve(@PathVariable("id") String productCode) {
        System.out.println(productService.reserve(productCode));
        return productService.reserve(productCode);
    }


    private ResponseEntity<?> validateFiles(ProductDto productDto) {
        List<String> allowedType = List.of("image/jpeg", "image/png", "image/gif");
        long maxFileSize = 3 * 1024 * 1024L;

        List<MultipartFile> files = new ArrayList<>();
        if (productDto.getImages() != null) {
            files.addAll(productDto.getImages());
        }
        if (productDto.getItinerary() != null) {
            files.addAll(productDto.getItinerary());
        }
        if (productDto.getReservation() != null) {
            files.addAll(productDto.getReservation());
        }
        if (productDto.getBusStop() != null) {
            files.addAll(productDto.getBusStop());
        }
        if (productDto.getCancellationPolicy() != null) {
            files.addAll(productDto.getCancellationPolicy());
        }

        if (!files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!allowedType.contains(file.getContentType())) {
                    return ResponseEntity.badRequest().body("지원하지 않는 형식입니다.");
                }

                if (file.getSize() > maxFileSize) {
                    return ResponseEntity.badRequest().body("파일 용량이 너무 큽니다.");
                }
            }
        }
        return null;
    }
}
