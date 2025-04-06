package com.example.tourback.set.schedule;

import com.example.tourback.set.schedule.querydsl.ScheduleQueryDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/findschedule/{id}")   // 상품 상세보기에서 달력에 사용하기 위해 만듦
    public List<ScheduleQueryDto> findSchedule(@PathVariable("id") String productCode) {
        return scheduleService.findSchedule(productCode);
    }
}
