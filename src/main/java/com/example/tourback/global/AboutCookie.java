package com.example.tourback.global;

import com.example.tourback.global.jwt.refresh.RefreshEntity;
import com.example.tourback.global.jwt.refresh.RefreshRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class AboutCookie {

    private final RefreshRepository refreshRepository;

    public void addCookieWithSameSite(HttpServletResponse response, String key, String value, String sameSite, int second) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(second);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        // 현재 응답의 Set-Cookie 헤더들을 가져옴
        Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
        boolean firstHeader = true;

        // 해당 키의 쿠키에만 SameSite 속성 추가
        for (String header : headers) {
            if (header.contains(key)) {
                // 기존 헤더 제거
                response.setHeader(HttpHeaders.SET_COOKIE, null);
                // 새 SameSite 속성이 추가된 헤더 설정
                response.addHeader(HttpHeaders.SET_COOKIE,
                        String.format("%s; SameSite=%s", header, sameSite));
                break;
            }
        }
    }

    public void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }
}
