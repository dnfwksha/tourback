package com.example.tourback.global.custom;

import com.example.tourback.set.member.Member;
import com.example.tourback.set.member.MemberRepository;
import com.example.tourback.set.member.querydsl.MemberRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username);
//        Member member = memberRepository.findByUsernameandDeleteYn(username,"N");
//        Member member = memberRepository.findByUsernameAndApprovedAndDeleteYn(username, "Y", "N");
        if (member != null) {
            return new CustomUserDetails(member);
        }

        return null;
    }
}
