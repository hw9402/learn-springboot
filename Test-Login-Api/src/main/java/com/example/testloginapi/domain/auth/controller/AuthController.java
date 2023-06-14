package com.example.testloginapi.domain.auth.controller;

import com.example.testloginapi.domain.auth.service.AuthService;
import com.example.testloginapi.domain.auth.service.util.GoogleOAuthRedirectUrl;
import com.example.testloginapi.global.jwt.dto.TokenResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final GoogleOAuthRedirectUrl googleOAuthRedirectUrl;
    private final AuthService authService;

    @GetMapping("/login")
    public void redirectGoogleLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(googleOAuthRedirectUrl.getOAuthRedirectUrl());
    }

    @PostMapping("/login/oauth2/authorization/{registrationId}")
    public TokenResponseDto oAuthLogin(@RequestParam(name = "code") String code,
                                       @PathVariable String registrationId) throws IOException {
        return ResponseEntity.ok(authService.login(code, registrationId)).getBody();
    }
}
