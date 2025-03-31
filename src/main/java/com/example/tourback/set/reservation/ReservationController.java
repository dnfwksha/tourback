package com.example.tourback.set.reservation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/save")
    public String save(@Valid @RequestBody ReservationRequestDto reservationRequestDto) {
        reservationService.save(reservationRequestDto);
        System.out.println("여기 오냐");
        return "등록 완료했습니다.";
    }
}
