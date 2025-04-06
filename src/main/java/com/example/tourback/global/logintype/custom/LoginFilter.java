package com.example.tourback.global.logintype.custom;

import com.example.tourback.global.AboutCookie;
import com.example.tourback.global.jwt.JwtUtil;
import com.example.tourback.global.jwt.refresh.RefreshEntity;
import com.example.tourback.global.jwt.refresh.RefreshRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AboutCookie aboutCookie;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, AboutCookie aboutCookie) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.aboutCookie = aboutCookie;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {


        InputStream is = null;
        try {
            is = request.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper mapper = new ObjectMapper();
        // JSON 데이터를 Map으로 읽어옴
        Map<String, String> authRequest = null;
        try {
            authRequest = new ObjectMapper().readValue(is, new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String username = authRequest.get("username");
        String password = authRequest.get("password");

        UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authtoken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = authResult.getName();
        Collection<? extends GrantedAuthority> collection = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = collection.iterator();
        String role = iterator.next().getAuthority();

        String access = jwtUtil.createJwt("access", username, role, 1000L * 60 * 10);
        String refresh = jwtUtil.createJwt("refresh", username, role, 1000L * 60 * 60 * 24);

        aboutCookie.addRefreshEntity(username, refresh, 1000 * 60 * 60 * 24L);

        response.setStatus(HttpServletResponse.SC_OK);
//        response.addHeader("Access-Control-Expose-Headers", "access");
//        response.setHeader("access", access);
//        response.addCookie(createCookie("refresh", refresh));

        aboutCookie.addCookieWithSameSite(response, "access", access, "Strict",60 * 10);
        aboutCookie.addCookieWithSameSite(response, "refresh", refresh, "Strict",60*60*24);


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        Map<String, Object> responseMap = new HashMap<>();
        Map<String, String> userMap = new HashMap<>();
        userMap.put("role", role);
        userMap.put("username", username);

        responseMap.put("user", userMap);
        responseMap.put("access", access);


        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(responseMap);
        out.write(jsonResponse);
        out.flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.setStatus(445);
    }
}
