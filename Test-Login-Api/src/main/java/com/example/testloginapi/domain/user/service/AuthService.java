package com.example.testloginapi.domain.user.service;

import com.example.testloginapi.domain.user.repository.UserRepository;
import com.example.testloginapi.global.auth.userinfo.CustomUserDetails;
import com.example.testloginapi.global.cookie.CookieUtil;
import com.example.testloginapi.global.jwt.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final String cookieKey = "refresh";

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public String refreshToken(HttpServletRequest request, HttpServletResponse response, String oldAccessToken) {
        // 1. Validation Refresh Token
        String oldRefreshToken = CookieUtil.getCookie(request, cookieKey)
                .map(Cookie::getValue).orElseThrow(() -> new RuntimeException("no Refresh Token Cookie"));

        if (!jwtProvider.validateToken(oldRefreshToken)) {
            throw new RuntimeException("Not Validated Refresh Token");
        }

        // 2. 유저 정보 얻기
        Authentication authentication = jwtProvider.getAuthentication(oldAccessToken);
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        Long id = Long.valueOf(user.getName());

        // 3. Match Refresh Token
        String savedToken = userRepository.getRefreshTokenById(id);

        if (!savedToken.equals(oldRefreshToken)) {
            throw new RuntimeException("Not Matched Refresh Token");
        }

        // 4. JWT 갱신
        String accessToken = jwtProvider.createAccessToken(authentication);
        jwtProvider.createRefreshToken(authentication, response);

        return accessToken;
    }
}
