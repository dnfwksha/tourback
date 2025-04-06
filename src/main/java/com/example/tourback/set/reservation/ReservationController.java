package com.example.tourback.set.reservation;

import com.example.tourback.set.reservation.querydsl.ReservationQueryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/save")
    public String save(@Valid @RequestBody ReservationRequestDto reservationRequestDto) {
        reservationService.save(reservationRequestDto);
        return "등록 완료했습니다.";
    }

    @GetMapping("/myorder")
    public List<ReservationQueryDto> myOrder() {
        return reservationService.myOrder();
    }
}
