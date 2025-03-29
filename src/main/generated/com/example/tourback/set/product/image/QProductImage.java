package com.example.tourback.set.product.image;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductImage is a Querydsl query type for ProductImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductImage extends EntityPathBase<ProductImage> {

    private static final long serialVersionUID = 1990260104L;

    public static final QProductImage productImage = new QProductImage("productImage");

    public final com.example.tourback.global.baseEntity.QImageBaseEntity _super = new com.example.tourback.global.baseEntity.QImageBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    //inherited
    public final StringPath delYn = _super.delYn;

    //inherited
    public final NumberPath<Integer> displayOrder = _super.displayOrder;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final EnumPath<ProductImage.ImageType> imageType = createEnum("imageType", ProductImage.ImageType.class);

    //inherited
    public final StringPath imageUrl = _super.imageUrl;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath productCode = createString("productCode");

    public QProductImage(String variable) {
        super(ProductImage.class, forVariable(variable));
    }

    public QProductImage(Path<? extends ProductImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductImage(PathMetadata metadata) {
        super(ProductImage.class, metadata);
    }

}

