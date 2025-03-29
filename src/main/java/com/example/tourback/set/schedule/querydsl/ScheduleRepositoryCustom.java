package com.example.tourback.set.schedule.querydsl;

import java.util.List;

public interface ScheduleRepositoryCustom {
    List<ScheduleQueryDto> findScheduleByProductCode(String productCode);
}
