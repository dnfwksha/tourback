package com.example.tourback.global.jwt.refresh;

import com.example.tourback.global.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReissueController {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        //get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {

                if (cookie.getName().equals("refresh")) {

                    refresh = cookie.getValue();
                }
            }
        }

        if (refresh == null) {

            //response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        //expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            //response status code
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {

            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        Boolean isExists = refreshRepository.existsByRefresh(refresh);
        if (!isExists) {
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT
//        String newAccess = jwtUtil.createJwt("access", username, role, 1000L * 10);
        String newAccess = jwtUtil.createJwt("access", username, role, 1000L * 60 * 10);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, 1000L * 60 * 60 * 24);

        try {
            refreshRepository.deleteByRefresh(refresh);
        } catch (Exception e) {

        }
        addRefreshEntity(username, newRefresh, 1000 * 60 * 60 * 24L);

        //response
//        response.addHeader("Access-Control-Expose-Headers", "access");
//        response.setHeader("access", newAccess);
//        response.addCookie(createCookie("refresh", newRefresh));

        addCookieWithSameSite(response, "access", newAccess, "Strict", 60 * 10);
        addCookieWithSameSite(response, "refresh", newRefresh, "Strict", 60 * 60 * 24);

        Map<String, Object> responseBody = new HashMap<>();
        Map<String, String> res = new HashMap<>();
        responseBody.put("access", newAccess);
        responseBody.put("user", res);
        res.put("role", role);
        res.put("username", username);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

//    private Cookie createCookie(String key, String value) {
//        Cookie cookie = new Cookie(key, value);
//        cookie.setMaxAge(60 * 60 * 24);
//        cookie.setPath("/");
//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);
//
//        return cookie;
//    }

    private void addCookieWithSameSite(HttpServletResponse response, String key, String value, String sameSite, int second) {
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

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }
}
