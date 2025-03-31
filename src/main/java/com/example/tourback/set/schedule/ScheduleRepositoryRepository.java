package com.example.tourback.set.schedule;

import com.example.tourback.set.schedule.querydsl.ScheduleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepositoryRepository extends JpaRepository<Schedule,Long>, ScheduleRepositoryCustom {
    Schedule findByProductCode(String productCode);
    List<Schedule> findDepartureDatesByProductCode(String productCode);

    void deleteByProductCode(String productCode);
}
