package com.example.tourback.global.s3service;


import com.example.tourback.set.homeSliderImage.HomeSliderImageRepository;
import com.example.tourback.set.product.image.ProductImageRepository;
import com.example.tourback.set.productDetail.image.ProductDetailImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class S3CleanUp {

    private final S3Client s3Client;
    private final HomeSliderImageRepository homeSliderImageRepository;
    private final ProductDetailImageRepository productDetailImageRepository;
    private final ProductImageRepository productImageRepository;


    @Value("${aws.s3.bucket}")
    private String bucketName;

    public S3CleanUp(S3Client s3Client, HomeSliderImageRepository homeSliderImageRepository, ProductDetailImageRepository productDetailImageRepository, ProductImageRepository productImageRepository) {
        this.s3Client = s3Client;
        this.homeSliderImageRepository = homeSliderImageRepository;
        this.productDetailImageRepository = productDetailImageRepository;
        this.productImageRepository = productImageRepository;
    }


    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanupUnusedS3Files() {
        log.info("Starting S3 cleanup task...");

        try {
            // 1. DB에서 사용 중인 파일명 가져오기
            Set<String> dbImageFileNames = new HashSet<>();

            // S3Image 테이블에서 파일명 가져오기
            homeSliderImageRepository.findAll().forEach(image -> {
                // 파일 경로에서 파일명만 추출 (경로 형식에 따라 조정 필요)
                String fileName = extractFileNameFromPath(image.getImageUrl());
                dbImageFileNames.add(fileName);
            });

            // S3Image2 테이블에서 파일명 가져오기
            productDetailImageRepository.findAll().forEach(image -> {
                String fileName = extractFileNameFromPath(image.getImageUrl());
                dbImageFileNames.add(fileName);
            });

            productImageRepository.findAll().forEach(image -> {
                String fileName = extractFileNameFromPath(image.getImageUrl());
                dbImageFileNames.add(fileName);
            });

            log.info("Total image files in DB: {}", dbImageFileNames.size());

            // 2. S3에서 모든 객체 목록 가져오기
            List<String> s3Objects = listS3Objects();
            log.info("Total objects in S3: {}", s3Objects.size());

            // 3. DB에 없는 S3 객체 삭제
            int deletedCount = 0;
            for (String s3ObjectKey : s3Objects) {
                String fileName = extractFileNameFromPath(s3ObjectKey);

                if (!dbImageFileNames.contains(fileName)) {
                    // DB에 없는 파일 삭제
                    DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                            .bucket(bucketName)
                            .key(s3ObjectKey)
                            .build();
                    s3Client.deleteObject(deleteRequest);
                    log.info("Deleted unused S3 object: {}", s3ObjectKey);
                    deletedCount++;
                }
            }

            log.info("S3 cleanup completed. Deleted {} unused objects", deletedCount);

        } catch (Exception e) {
            log.error("Error during S3 cleanup task", e);
        }
    }

    private List<String> listS3Objects() {
        List<String> objectKeys = new ArrayList<>();

        // S3Client v2 방식으로 객체 리스팅
        ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response response;
        String continuationToken = null;

        do {
            // 연속 토큰이 있으면 설정
            if (continuationToken != null) {
                listRequest = listRequest.toBuilder()
                        .continuationToken(continuationToken)
                        .build();
            }

            response = s3Client.listObjectsV2(listRequest);

            for (S3Object s3Object : response.contents()) {
                objectKeys.add(s3Object.key());
            }

            continuationToken = response.nextContinuationToken();
        } while (response.isTruncated());

        return objectKeys;
    }

    private String extractFileNameFromPath(String path) {
        // URL이나 경로에서 파일명만 추출
        // 예: "https://bucket.s3.region.amazonaws.com/images/photo.jpg" -> "photo.jpg"
        // 또는 "/images/photo.jpg" -> "photo.jpg"
        if (path == null) return "";

        // 경로에서 마지막 슬래시 이후의 문자열을 파일명으로 간주
        int lastSlashIndex = path.lastIndexOf('/');
        if (lastSlashIndex >= 0 && lastSlashIndex < path.length() - 1) {
            return path.substring(lastSlashIndex + 1);
        }

        return path;
    }
}