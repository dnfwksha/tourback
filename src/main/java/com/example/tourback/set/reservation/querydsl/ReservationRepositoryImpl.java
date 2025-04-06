package com.example.tourback.set.reservation.querydsl;

import com.example.tourback.set.reservation.Reservation;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.tourback.set.product.QProduct.product;
import static com.example.tourback.set.reservation.QReservation.reservation;

@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReservationQueryDto> myOrder(String currentUsername) {
        return jpaQueryFactory
                .select(Projections.bean(ReservationQueryDto.class,
                        reservation.bookingCode,
                        reservation.productCode,
                        reservation.departureDate,
                        reservation.arrivalDate,
                        reservation.numberOfTravelers,
                        reservation.totalCost,
                        reservation.paymentType,
                        reservation.paymentStatus,
                        reservation.name,
                        reservation.phone,
                        reservation.createdAt,
                        product.title
                ))
                .from(reservation)
                .leftJoin(product)
                .on(reservation.productCode.eq(product.productCode))
                .where(reservation.username.eq(currentUsername))
                .fetch();
    }
}
