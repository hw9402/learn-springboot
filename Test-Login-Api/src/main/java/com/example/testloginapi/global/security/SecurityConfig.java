package com.example.testloginapi.global.security;

import com.example.testloginapi.global.auth.handler.JwtAccessDeniedHandler;
import com.example.testloginapi.global.auth.handler.JwtAuthenticationEntryPoint;
import com.example.testloginapi.global.auth.handler.OAuth2AuthenticationFailureHandler;
import com.example.testloginapi.global.auth.handler.OAuth2AuthenticationSuccessHandler;
import com.example.testloginapi.global.auth.service.CustomOAuth2UserService;
import com.example.testloginapi.global.cookie.repository.CookieAuthorizationRequestRepository;
import com.example.testloginapi.global.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeHttpRequests()
                    .requestMatchers("/oauth2/**", "/auth/**").permitAll()
                    .requestMatchers("/admin/**").hasRole("ROLE_ADMIN")
                    .anyRequest().authenticated();

        http
                .formLogin().disable()
                .oauth2Login()
                    .authorizationEndpoint()
                        .baseUri("/oauth2/authorize")
                        .authorizationRequestRepository(cookieAuthorizationRequestRepository)
                    .and()
                    .redirectionEndpoint()
                        .baseUri("/oauth2/callback/*")
                    .and()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                    .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler);

        http.exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 401
                .accessDeniedHandler(jwtAccessDeniedHandler);          // 403

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
