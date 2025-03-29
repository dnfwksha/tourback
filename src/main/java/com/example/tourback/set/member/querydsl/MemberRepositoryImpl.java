package com.example.tourback.set.member.querydsl;

import com.example.tourback.set.member.Member;
import com.example.tourback.set.member.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.example.tourback.set.member.QMember.member;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Member getQslMember(String username) {
//        QMember member = QMember.member;

        return jpaQueryFactory.selectFrom(member).where(member.username.eq(username)).fetchOne();
    }
}
