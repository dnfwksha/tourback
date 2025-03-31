package com.example.tourback.set.homeSliderImage;

import com.example.tourback.set.homeSliderImage.querydsl.HomeSliderImageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeSliderImageRepository extends JpaRepository<HomeSliderImage,Long>, HomeSliderImageRepositoryCustom {
}
