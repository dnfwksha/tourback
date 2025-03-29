package com.example.tourback.set.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 446136740L;

    public static final QMember member = new QMember("member1");

    public final com.example.tourback.global.baseEntity.QBaseEntity _super = new com.example.tourback.global.baseEntity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath delYn = _super.delYn;

    public final StringPath emailVerified = createString("emailVerified");

    public final NumberPath<Integer> failedLoginAttempts = createNumber("failedLoginAttempts", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.time.LocalDateTime> lastLoginDate = createDateTime("lastLoginDate", java.time.LocalDateTime.class);

    public final StringPath marketingConsent = createString("marketingConsent");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final DateTimePath<java.time.LocalDateTime> passwordChangeDate = createDateTime("passwordChangeDate", java.time.LocalDateTime.class);

    public final StringPath phone = createString("phone");

    public final StringPath phoneVerified = createString("phoneVerified");

    public final EnumPath<Member.Role> role = createEnum("role", Member.Role.class);

    public final StringPath username = createString("username");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

