package com.example.tourback.global.config;

import com.example.tourback.global.AboutCookie;
import com.example.tourback.global.logintype.custom.CustomLogoutFilter;
import com.example.tourback.global.logintype.custom.LoginFilter;
import com.example.tourback.global.jwt.JwtFilter;
import com.example.tourback.global.jwt.JwtUtil;
import com.example.tourback.global.jwt.refresh.RefreshRepository;
import com.example.tourback.global.logintype.oauth2.service.CustomOAuth2UserService;
import com.example.tourback.global.logintype.oauth2.service.CustomSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
//    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final RefreshRepository refreshRepository;
    private final AboutCookie aboutCookie;

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;

    private final JwtUtil jwtUtil;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer(){
//        return web -> {
//            web.ignoring()
//                    .requestMatchers(
//                            "/public/",
//                            "/login",
//                            "/reissue",
//                            "/oauth2/authorization/",
//                            "/api/user/me",
//                            "/api/product/main",
//                            "/api/homesliderimage/all",
//                            "/api/sitevisit/count",
//                            "/api/community/all"
//                    );
//        };
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/login","/reissue","/api/user/me").permitAll()
                        .requestMatchers("/api/product/main","/api/community/all").permitAll()
                        .requestMatchers("/api/product/**").permitAll()
                        .requestMatchers("/api/favorite/check/**").permitAll()
                        .requestMatchers("/api/homesliderimage/all").permitAll()
                        .requestMatchers("/api/**").authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, aboutCookie), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class)
//                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class)

//                .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class)
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler)
                )
        ;
        return http.build();
    }
}
