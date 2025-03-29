package com.example.tourback.set.member;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.tourback.set.member.Member.Role.ROLE_USER;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void join(MemberDto memberDto) {
        if(memberDto.getUsername() == null){
            throw new RuntimeException("username is null");
        }

        Member member = new Member();
        member.setUsername(memberDto.getUsername());
        member.setPassword(bCryptPasswordEncoder.encode(memberDto.getPassword()));
        member.setName(memberDto.getName());
//        member.setEmail(memberDto.getEmail());
        member.setPhone(memberDto.getPhone());
        member.setRole(ROLE_USER);
        memberRepository.save(member);
    }

//    public String findById(String username) {
//        Member aa = memberRepository.findByUsername(username);
//        if (aa == null) {
//            return "사용 가능한 아이디입니다.";
//        } else {
//            return "사용 중인 아이디입니다.";
//        }
//    }

}
