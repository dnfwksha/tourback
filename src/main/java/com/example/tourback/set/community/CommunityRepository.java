package com.example.tourback.set.community;

import com.example.tourback.set.community.querydsl.CommunityRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityRepositoryCustom {
}
