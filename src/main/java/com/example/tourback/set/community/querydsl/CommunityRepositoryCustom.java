package com.example.tourback.set.community.querydsl;

import java.util.List;

public interface CommunityRepositoryCustom {
    List<CommunityQueryDto> findAllCommunity();
    CommunityQueryDto findOneCommunity(String communityCode);
}
