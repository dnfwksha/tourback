package com.example.tourback.set.schedule.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.tourback.set.schedule.QSchedule.schedule;
import static com.querydsl.jpa.JPAExpressions.select;
@RequiredArgsConstructor
public class ScheduleRepositoryRepositoryImpl implements ScheduleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ScheduleQueryDto> findScheduleByProductCode(String productCode) {
        return jpaQueryFactory
                .select(Projections.bean(ScheduleQueryDto.class,
                        schedule.departureDate))
                .from(schedule)
                .where(schedule.productCode.eq(productCode))
                .fetch();
    }
}
