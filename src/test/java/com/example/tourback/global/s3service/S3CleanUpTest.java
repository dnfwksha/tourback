package com.example.tourback.global.s3service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class S3CleanUpTest {

    @Autowired
    private S3CleanUp s3CleanUp;

    @Test
    public void testCleanupUnusedS3Files() {
        // 실행
        s3CleanUp.cleanupUnusedS3Files();

        // 여기서 필요한 검증 로직을 추가할 수 있습니다
        // 예: S3 파일 목록 확인 또는 로그 확인
    }
}