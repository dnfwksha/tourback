package com.example.tourback.set.reservation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReservation is a Querydsl query type for Reservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservation extends EntityPathBase<Reservation> {

    private static final long serialVersionUID = 2104025632L;

    public static final QReservation reservation = new QReservation("reservation");

    public final com.example.tourback.global.baseEntity.QBaseEntity _super = new com.example.tourback.global.baseEntity.QBaseEntity(this);

    public final DatePath<java.time.LocalDate> arrivalDate = createDate("arrivalDate", java.time.LocalDate.class);

    public final StringPath boardingLocation = createString("boardingLocation");

    public final StringPath bookingCode = createString("bookingCode");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath delYn = _super.delYn;

    public final DatePath<java.time.LocalDate> departureDate = createDate("departureDate", java.time.LocalDate.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath note = createString("note");

    public final NumberPath<Integer> numberOfTravelers = createNumber("numberOfTravelers", Integer.class);

    public final EnumPath<Reservation.PaymentStatus> paymentStatus = createEnum("paymentStatus", Reservation.PaymentStatus.class);

    public final StringPath paymentType = createString("paymentType");

    public final StringPath phone = createString("phone");

    public final StringPath productCode = createString("productCode");

    public final NumberPath<Integer> totalCost = createNumber("totalCost", Integer.class);

    public final StringPath username = createString("username");

    public QReservation(String variable) {
        super(Reservation.class, forVariable(variable));
    }

    public QReservation(Path<? extends Reservation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReservation(PathMetadata metadata) {
        super(Reservation.class, metadata);
    }

}

