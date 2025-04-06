package com.example.tourback.global.logintype.oauth2.service;

import com.example.tourback.global.logintype.oauth2.dto.KakaoResponse;
import com.example.tourback.global.logintype.oauth2.dto.NaverResponse;
import com.example.tourback.global.logintype.oauth2.dto.OAuth2Response;
import com.example.tourback.set.member.Member;
import com.example.tourback.set.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import static com.example.tourback.set.member.Member.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("-----------------------------------");
        System.out.println(oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("kakao")) {
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String email = oAuth2Response.getEmail();
        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        Member existData = memberRepository.findByUsername(email);

        if (existData == null) {

            Member member = new Member();
            member.setUsername(email);
            member.setName(oAuth2Response.getName());
            member.setPassword("oauth2");
            member.setEmailVerified("oauth2");
            member.setPhoneVerified("oauth2");
            member.setMarketingConsent("oauth2");
            member.setPhone("oauth2 will give");
            member.setRole(ROLE_USER);

            memberRepository.save(member);
            System.out.println("멤버겟유저네임 : "+member.getUsername());

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(email);
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole("ROLE_USER");

            System.out.println("if UserDto : "+userDTO);
            return new CustomOAuth2User(userDTO);
        }
        else {

            existData.setUsername(email);
            existData.setName(oAuth2Response.getName());
            existData.setPassword("oauth2");
            System.out.println("existData : "+existData.getUsername());

            memberRepository.save(existData);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(email);
            userDTO.setName(existData.getName());
            userDTO.setRole(existData.getRole().name());
            System.out.println("else UserDto : "+userDTO);
            return new CustomOAuth2User(userDTO);
        }
    }
}
