package com.example.tourback.set.reservableDate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReservableDate is a Querydsl query type for ReservableDate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservableDate extends EntityPathBase<ReservableDate> {

    private static final long serialVersionUID = -901830460L;

    public static final QReservableDate reservableDate = new QReservableDate("reservableDate");

    public final DatePath<java.time.LocalDate> availableDate = createDate("availableDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath productCode = createString("productCode");

    public QReservableDate(String variable) {
        super(ReservableDate.class, forVariable(variable));
    }

    public QReservableDate(Path<? extends ReservableDate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReservableDate(PathMetadata metadata) {
        super(ReservableDate.class, metadata);
    }

}

