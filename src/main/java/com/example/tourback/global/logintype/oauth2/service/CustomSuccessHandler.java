package com.example.tourback.global.logintype.oauth2.service;

import com.example.tourback.global.AboutCookie;
import com.example.tourback.global.jwt.JwtUtil;
import com.example.tourback.global.jwt.refresh.RefreshRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final AboutCookie aboutCookie;

    public CustomSuccessHandler(JwtUtil jwtUtil, RefreshRepository refreshRepository, AboutCookie aboutCookie) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.aboutCookie = aboutCookie;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        String role = iterator.next().getAuthority();

        String access = jwtUtil.createJwt("access", username, role, 1000L * 60 * 15);
        String refresh = jwtUtil.createJwt("refresh", username, role, 1000L * 60 * 60 * 24);

        aboutCookie.addRefreshEntity(username, refresh, 1000 * 60 * 60 * 24L);

        response.setStatus(HttpServletResponse.SC_OK);
        aboutCookie.addCookieWithSameSite(response, "access", access, "Strict", 60 * 15);
        aboutCookie.addCookieWithSameSite(response, "refresh", refresh, "Strict", 60 * 60 * 24);


        response.sendRedirect("http://localhost:3000/");
    }


}
