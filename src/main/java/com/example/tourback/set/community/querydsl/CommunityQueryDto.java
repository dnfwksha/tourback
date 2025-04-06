package com.example.tourback.set.community.querydsl;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommunityQueryDto {
    private String title;
    private String communityCode;
    private LocalDateTime createdAt;
    private int viewCount;

    private List<ImageDto> communityImages;
    @Getter
    @Setter
    public static class ImageDto {
        private String imageUrl;
        private int displayOrder;
    }
}
