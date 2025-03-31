package com.example.tourback.global.s3service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class S3Upload {

    private final String bucketName;
    private final String region;
    private final S3Client s3Client;

    public S3Upload(
            @Value("${aws.s3.bucket}") String bucketName,
            @Value("${aws.region}") String region,
            S3Client s3Client
    ) {
        this.bucketName = bucketName;
        this.region = region;
        this.s3Client = s3Client;
    }

    public String uploadFileToS3(MultipartFile file, String fileName) throws IOException {
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
}
