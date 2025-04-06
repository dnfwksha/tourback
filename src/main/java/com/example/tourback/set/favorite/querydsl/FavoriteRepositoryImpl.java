package com.example.tourback.set.favorite.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.tourback.set.favorite.QFavorite.favorite;
import static com.example.tourback.set.member.QMember.member;
import static com.example.tourback.set.product.QProduct.product;

@RequiredArgsConstructor
public class FavoriteRepositoryImpl implements FavoriteRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FavoriteQueryDto> findAllFavorite(String currentUsername) {
        return jpaQueryFactory
                .select(Projections.bean(FavoriteQueryDto.class,
                        favorite.productCode,
                        favorite.username,
                        product.title
                ))
                .from(favorite)
                .join(product)
                .on(product.productCode.eq(favorite.productCode))
                .join(member)
                .on(member.username.eq(favorite.username))
                .where(member.username.eq(currentUsername))
                .fetch();
    }
}
