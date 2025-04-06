package com.example.tourback.set.community;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class CommunityDto {

    private String title;
    private List<MultipartFile> images;//리스트 이미지
}
