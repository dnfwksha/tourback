package com.example.tourback.set.reservation.querydsl;

import java.util.List;

public interface ReservationRepositoryCustom {
    List<ReservationQueryDto> myOrder(String currentUsername);
}
