package com.example.tourback.set.homeSliderImage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class HomeSliderImageDto {

    private List<MultipartFile> images;
}
