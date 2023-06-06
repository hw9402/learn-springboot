package com.example.testloginapi.global.auth.handler;

import com.example.testloginapi.global.cookie.repository.CookieAuthorizationRequestRepository;
import com.example.testloginapi.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final String redirectUri = "http://localhost:3000/oauth2/redirect";
    private final JwtProvider jwtProvider;
    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
}
