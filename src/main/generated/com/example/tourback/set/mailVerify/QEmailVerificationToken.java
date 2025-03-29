package com.example.tourback.set.mailVerify;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmailVerificationToken is a Querydsl query type for EmailVerificationToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmailVerificationToken extends EntityPathBase<EmailVerificationToken> {

    private static final long serialVersionUID = 901571894L;

    public static final QEmailVerificationToken emailVerificationToken = new QEmailVerificationToken("emailVerificationToken");

    public final StringPath email = createString("email");

    public final DateTimePath<java.time.LocalDateTime> expiryDate = createDateTime("expiryDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath token = createString("token");

    public final BooleanPath verified = createBoolean("verified");

    public QEmailVerificationToken(String variable) {
        super(EmailVerificationToken.class, forVariable(variable));
    }

    public QEmailVerificationToken(Path<? extends EmailVerificationToken> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmailVerificationToken(PathMetadata metadata) {
        super(EmailVerificationToken.class, metadata);
    }

}

