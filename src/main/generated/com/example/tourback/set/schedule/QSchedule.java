package com.example.tourback.set.schedule;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSchedule is a Querydsl query type for Schedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchedule extends EntityPathBase<Schedule> {

    private static final long serialVersionUID = 266339908L;

    public static final QSchedule schedule = new QSchedule("schedule");

    public final com.example.tourback.global.baseEntity.QBaseEntity _super = new com.example.tourback.global.baseEntity.QBaseEntity(this);

    public final DatePath<java.time.LocalDate> arrivalDate = createDate("arrivalDate", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath delYn = _super.delYn;

    public final DatePath<java.time.LocalDate> departureDate = createDate("departureDate", java.time.LocalDate.class);

    public final StringPath domestic = createString("domestic");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Integer> maxParticipants = createNumber("maxParticipants", Integer.class);

    public final NumberPath<Integer> minParticipants = createNumber("minParticipants", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> nowParticipants = createNumber("nowParticipants", Integer.class);

    public final NumberPath<Integer> period = createNumber("period", Integer.class);

    public final StringPath productCode = createString("productCode");

    public QSchedule(String variable) {
        super(Schedule.class, forVariable(variable));
    }

    public QSchedule(Path<? extends Schedule> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSchedule(PathMetadata metadata) {
        super(Schedule.class, metadata);
    }

}

