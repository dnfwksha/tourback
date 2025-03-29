package com.example.tourback.set.member.querydsl;

import com.example.tourback.set.member.Member;

public interface MemberRepositoryCustom {
    Member getQslMember(String username);
}
