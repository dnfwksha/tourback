package com.example.tourback.set.productDetail.image;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductDetailImage is a Querydsl query type for ProductDetailImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductDetailImage extends EntityPathBase<ProductDetailImage> {

    private static final long serialVersionUID = -372535706L;

    public static final QProductDetailImage productDetailImage = new QProductDetailImage("productDetailImage");

    public final com.example.tourback.global.baseEntity.QImageBaseEntity _super = new com.example.tourback.global.baseEntity.QImageBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    //inherited
    public final StringPath delYn = _super.delYn;

    //inherited
    public final NumberPath<Integer> displayOrder = _super.displayOrder;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final EnumPath<ProductDetailImage.ImageType> imageType = createEnum("imageType", ProductDetailImage.ImageType.class);

    //inherited
    public final StringPath imageUrl = _super.imageUrl;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath productCode = createString("productCode");

    public QProductDetailImage(String variable) {
        super(ProductDetailImage.class, forVariable(variable));
    }

    public QProductDetailImage(Path<? extends ProductDetailImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductDetailImage(PathMetadata metadata) {
        super(ProductDetailImage.class, metadata);
    }

}

