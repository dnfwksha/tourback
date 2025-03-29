package com.example.tourback.set.schedule;

import com.example.tourback.set.schedule.querydsl.ScheduleQueryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepositoryRepository scheduleRepository;

    public ScheduleService(ScheduleRepositoryRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<ScheduleQueryDto> findSchedule(String productCode) {
        return scheduleRepository.findScheduleByProductCode(productCode);
    }
}
