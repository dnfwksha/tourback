package com.example.tourback.set.member;

import com.example.tourback.set.member.querydsl.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String>, MemberRepositoryCustom {
    Member findByUsername(String username);

    boolean existsByUsername(String username);
//    boolean existsByEmail(String email);

//    Member findByUsernameandDeleteYn(String username, String delYn);
}
