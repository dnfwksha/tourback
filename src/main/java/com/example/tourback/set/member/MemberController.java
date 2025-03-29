package com.example.tourback.set.member;

import com.example.tourback.global.RsData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberDto memberDto) {
        System.out.println(memberDto);
        memberService.join(memberDto);
        return ResponseEntity.ok(RsData.of(null,"회원가입 완료되었습니다.",null));
    }

//    @PostMapping("/findid")
//    public String findid(@RequestBody String username) {
//        return memberService.findById(username);
//    }
}
