package com.example.testloginapi.domain.auth.controller;

import com.example.testloginapi.domain.auth.service.AuthService;
import com.example.testloginapi.domain.auth.service.util.GoogleOAuthRedirectUrl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AuthLoginController {

    private final GoogleOAuthRedirectUrl googleOAuthRedirectUrl;
    private final AuthService authService;

    @GetMapping("/login")
    public void redirectGoogleLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(googleOAuthRedirectUrl.getOAuthRedirectUrl());
    }

    @GetMapping("/login/oauth2/authorization/{registrationId}")
    public void oAuthLogin(@RequestParam(name = "code") String code,
                               @PathVariable String registrationId) throws IOException {
        authService.login(code, registrationId);
    }
}
