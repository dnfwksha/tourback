package com.example.tourback.set.homeSliderImage;

import com.example.tourback.global.RsData;
import com.example.tourback.set.homeSliderImage.querydsl.HomeSliderImageQueryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/homesliderimage")
public class HomeSliderImageController {

    private final HomeSliderImageService homeSliderImageService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid HomeSliderImageDto homeSliderImageDto) throws IOException {

        System.out.println(homeSliderImageDto.getImages());
        List<String> allowedType = List.of("image/jpeg", "image/png", "image/gif");
        long maxFileSize = 3 * 1024 * 1024L;

        List<MultipartFile> filesAll = new ArrayList<>();
        filesAll.addAll(homeSliderImageDto.getImages());

        for (MultipartFile file : filesAll) {
            if (!allowedType.contains(file.getContentType())) {
                return ResponseEntity.badRequest().body(RsData.of(null, "지원하지 않는 형식입니다.", null));
            }

            if (file.getSize() > maxFileSize) {
                return ResponseEntity.badRequest().body(RsData.of(null, "파일 용량이 너무 큽니다.", null));
            }
        }
        homeSliderImageService.create(homeSliderImageDto);
        return ResponseEntity.ok(RsData.of(null, "배너 이미지 등록 완료되었습니다.", null));
    }

    @GetMapping("/all")
    public List<HomeSliderImageQueryDto> all() {
        return homeSliderImageService.all();
    }
}
