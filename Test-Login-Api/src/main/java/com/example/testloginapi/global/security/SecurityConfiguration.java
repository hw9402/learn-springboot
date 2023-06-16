package com.example.testloginapi.global.security;

import com.example.testloginapi.domain.user.domain.Role;
import com.example.testloginapi.global.jwt.filter.JwtAuthenticationFilter;
import com.example.testloginapi.global.jwt.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtProvider jwtProvider;

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
                                .requestMatchers("/login/**").permitAll() // 구글 로그인 관련 컨트롤러
                                .requestMatchers("/api/user/**").hasAuthority("ROLE_USER") // jwt 테스트를 위한 유저 정보 가져오기 테스트 컨트롤러
                                .anyRequest().authenticated()
                );

        http
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
