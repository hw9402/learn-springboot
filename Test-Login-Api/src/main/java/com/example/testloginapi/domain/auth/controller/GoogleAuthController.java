package com.example.testloginapi.domain.auth.controller;

import com.example.testloginapi.domain.auth.service.GoogleOAuthService;
import com.example.testloginapi.domain.user.domain.dto.UserDto;
import com.example.testloginapi.domain.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class GoogleAuthController {

    private final GoogleOAuthService googleOAuthService;
    private final UserService userService;

    @GetMapping("/api/auth/google/login")
    public void redirectGoogleLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(googleOAuthService.getOAuthRedirectUrl());
    }

    @GetMapping("/api/auth/google/access")
    public String getTokenInfo(@RequestParam(name = "code") String code) throws IOException {
        return userService.save(googleOAuthService.tokenInfo(code));
    }

    @GetMapping("/api/auth/google/info/{id}")
    public UserDto getAccountInfo(@PathVariable Long id) {
        return userService.findOne(id);
    }
}
