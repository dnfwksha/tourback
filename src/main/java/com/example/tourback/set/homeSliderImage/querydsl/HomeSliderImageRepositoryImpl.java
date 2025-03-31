package com.example.tourback.set.homeSliderImage.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.example.tourback.set.homeSliderImage.QHomeSliderImage.homeSliderImage;

public class HomeSliderImageRepositoryImpl implements HomeSliderImageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public HomeSliderImageRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<HomeSliderImageQueryDto> findImageUrlandDisplayOrder() {
        return jpaQueryFactory
                .select(Projections.bean(HomeSliderImageQueryDto.class,
                        homeSliderImage.imageUrl,
                        homeSliderImage.displayOrder
                ))
                .from(homeSliderImage)
                .fetch();
    }
}
