package com.example.tourback.set.homeSliderImage;

import com.example.tourback.global.s3service.S3Upload;
import com.example.tourback.set.homeSliderImage.querydsl.HomeSliderImageQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeSliderImageService {

    private final HomeSliderImageRepository homeSliderImageRepository;
    private final S3Upload s3Upload;


    public void create(HomeSliderImageDto homeSliderImageDto) throws IOException {

        homeSliderImageRepository.deleteAll();
        List<MultipartFile> files = homeSliderImageDto.getImages();

        for (int i = 0; i < files.size(); i++) {
            MultipartFile image = files.get(i);
            String originalFilename = image.getOriginalFilename();
            String imageFilename = System.currentTimeMillis() + "mainbanner_" + originalFilename;
            String imageUrl = s3Upload.uploadFileToS3(image, imageFilename);

            HomeSliderImage homeSliderImage = new HomeSliderImage();
            homeSliderImage.setImageUrl(imageUrl);
            homeSliderImage.setDisplayOrder(i + 1);
            homeSliderImageRepository.save(homeSliderImage);
        }
    }

    public List<HomeSliderImageQueryDto> all() {
        return homeSliderImageRepository.findImageUrlandDisplayOrder();
    }
}
