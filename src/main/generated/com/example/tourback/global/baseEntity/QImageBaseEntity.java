package com.example.tourback.global.baseEntity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QImageBaseEntity is a Querydsl query type for ImageBaseEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QImageBaseEntity extends EntityPathBase<ImageBaseEntity> {

    private static final long serialVersionUID = -371784542L;

    public static final QImageBaseEntity imageBaseEntity = new QImageBaseEntity("imageBaseEntity");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final StringPath delYn = createString("delYn");

    public final NumberPath<Integer> displayOrder = createNumber("displayOrder", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public QImageBaseEntity(String variable) {
        super(ImageBaseEntity.class, forVariable(variable));
    }

    public QImageBaseEntity(Path<? extends ImageBaseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QImageBaseEntity(PathMetadata metadata) {
        super(ImageBaseEntity.class, metadata);
    }

}

