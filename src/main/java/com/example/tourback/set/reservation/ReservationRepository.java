package com.example.tourback.set.reservation;

import com.example.tourback.set.reservation.querydsl.ReservationRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long>, ReservationRepositoryCustom {
}
