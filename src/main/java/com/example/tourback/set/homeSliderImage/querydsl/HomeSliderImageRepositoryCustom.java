package com.example.tourback.set.homeSliderImage.querydsl;

import com.example.tourback.set.homeSliderImage.HomeSliderImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeSliderImageRepositoryCustom {

    List<HomeSliderImageQueryDto> findImageUrlandDisplayOrder();

}
