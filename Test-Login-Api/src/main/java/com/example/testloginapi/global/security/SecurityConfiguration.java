package com.example.testloginapi.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .cors();

        http
                .authorizeHttpRequests(
                        author -> author
                                .requestMatchers("/").permitAll() // 구글 로그인 테스트를 위한 메인화면
                                .requestMatchers("/login/**").permitAll() // 구글 로그인 관련 컨트롤러 url
                                .anyRequest().authenticated()
                );

        return http.build();
    }
}
