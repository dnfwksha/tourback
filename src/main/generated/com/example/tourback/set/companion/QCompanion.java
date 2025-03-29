package com.example.tourback.set.companion;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompanion is a Querydsl query type for Companion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompanion extends EntityPathBase<Companion> {

    private static final long serialVersionUID = 1933533984L;

    public static final QCompanion companion = new QCompanion("companion");

    public final StringPath boardingLocation = createString("boardingLocation");

    public final StringPath bookingCode = createString("bookingCode");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> numberOfPeople = createNumber("numberOfPeople", Integer.class);

    public final StringPath phone = createString("phone");

    public QCompanion(String variable) {
        super(Companion.class, forVariable(variable));
    }

    public QCompanion(Path<? extends Companion> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompanion(PathMetadata metadata) {
        super(Companion.class, metadata);
    }

}

