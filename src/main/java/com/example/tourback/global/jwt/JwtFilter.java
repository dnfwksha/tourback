package com.example.tourback.global.jwt;

import com.example.tourback.global.logintype.custom.CustomUserDetails;
import com.example.tourback.set.member.Member;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {   //  jwt 토큰 검증 클래스

    private final JwtUtil jwtUtil;

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        String path = request.getRequestURI();
//
//        // 필터를 적용하지 않을 경로 지정
//        return path.startsWith("/public/") ||
//                path.equals("/login") ||
//                path.equals("/reissue") ||
//                path.startsWith("/oauth2/authorization/") ||
//                path.equals("/api/user/me") ||
//                path.equals("/api/product/main") ||
//                path.equals("/api/homesliderimage/all") ||
//                path.equals("/api/sitevisit/count") ||
//                path.equals("/api/community/all");
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = null;
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {

                if (cookie.getName().equals("access")) {

                    accessToken = cookie.getValue();
                }
                if (cookie.getName().equals("refresh")) {

                    refreshToken = cookie.getValue();
                }
            }
        }
        System.out.println("토큰토큰");
        System.out.println(accessToken);
        System.out.println(refreshToken);

        //Authorization 헤더 검증
        if (accessToken == null) {
            if(refreshToken != null) {
                response.setStatus(499);
            }
            System.out.println("ACCESS토큰이 없습니다.");
            filterChain.doFilter(request, response);

            return;
        }

        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            PrintWriter writer = response.getWriter();
            writer.println("토큰이 만료되었습니다.");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String category = jwtUtil.getCategory(accessToken);
        if (!category.equals("access")) {
            PrintWriter writer = response.getWriter();
            writer.println("잘못된 토큰입니다.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        //토큰에서 username과 role 획득
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        //userEntity를 생성하여 값 set
        Member member = new Member();
        member.setUsername(username);
        member.setPassword("aaaa");
        member.setRole(Member.Role.valueOf(role));

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }
}
