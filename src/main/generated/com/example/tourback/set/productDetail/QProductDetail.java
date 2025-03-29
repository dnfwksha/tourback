package com.example.tourback.set.productDetail;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductDetail is a Querydsl query type for ProductDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductDetail extends EntityPathBase<ProductDetail> {

    private static final long serialVersionUID = -1645409976L;

    public static final QProductDetail productDetail = new QProductDetail("productDetail");

    public final com.example.tourback.global.baseEntity.QBaseEntity _super = new com.example.tourback.global.baseEntity.QBaseEntity(this);

    public final StringPath accommodationInfo = createString("accommodationInfo");

    public final StringPath arrivalLocation = createString("arrivalLocation");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath delYn = _super.delYn;

    public final StringPath departAt = createString("departAt");

    public final StringPath departureLocation = createString("departureLocation");

    public final StringPath excludedServices = createString("excludedServices");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath includedServices = createString("includedServices");

    public final StringPath itinerary = createString("itinerary");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath productCode = createString("productCode");

    public final StringPath recommend = createString("recommend");

    public final StringPath transportationType = createString("transportationType");

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QProductDetail(String variable) {
        super(ProductDetail.class, forVariable(variable));
    }

    public QProductDetail(Path<? extends ProductDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductDetail(PathMetadata metadata) {
        super(ProductDetail.class, metadata);
    }

}

