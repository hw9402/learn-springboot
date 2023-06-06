package com.example.testloginapi.domain.user.controller;

import com.example.testloginapi.domain.user.domain.User;
import com.example.testloginapi.domain.user.repository.UserRepository;
import com.example.testloginapi.global.auth.userinfo.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public User getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("not found user"));
    }
}
