package com.example.tourback.set.community.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.tourback.set.community.QCommunity.community;
import static com.example.tourback.set.community.image.QCommunityImage.communityImage;

@RequiredArgsConstructor
public class CommunityRepositoryImpl implements CommunityRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CommunityQueryDto> findAllCommunity() {
        //communityCode,title,createAt
        return jpaQueryFactory
                .select(Projections.bean(CommunityQueryDto.class,
                        community.communityCode,
                        community.title,
                        community.createdAt
                ))
                .from(community)
                .fetch();
    }

    @Override
    public CommunityQueryDto findOneCommunity(String communityCode) {
        CommunityQueryDto result = jpaQueryFactory
                .select(Projections.bean(CommunityQueryDto.class,
                        community.communityCode,
                        community.title,
                        community.viewCount
                ))
                .from(community)
                .where(community.communityCode.eq(communityCode))
                .fetchOne();
        if (result != null) {
            List<CommunityQueryDto.ImageDto> images = jpaQueryFactory
                    .select(Projections.bean(CommunityQueryDto.ImageDto.class,
                            communityImage.imageUrl,
                            communityImage.displayOrder
                    ))
                    .from(communityImage)
                    .where(communityImage.communityCode.eq(communityCode))
                    .fetch();

            result.setCommunityImages(images);
        }
        return result;
    }


}
