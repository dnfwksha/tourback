package com.example.tourback.global.baseEntity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public class ImageBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // S3에 저장된 전체 이미지의 URL
    private String imageUrl;

    // 이미지 순서 (여러 이미지가 있을 경우 정렬용)
    private int displayOrder;

    // 이미지 상태 (활성/비활성)
    @Builder.Default
    private String delYn = "N";

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

}
