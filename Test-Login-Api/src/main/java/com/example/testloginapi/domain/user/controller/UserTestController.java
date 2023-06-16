package com.example.testloginapi.domain.user.controller;

import com.example.testloginapi.domain.user.domain.User;
import com.example.testloginapi.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserTestController {

    private final UserRepository userRepository;

    @GetMapping("/api/user/{id}")
    public User testController(@PathVariable Long id) {
        return userRepository.findById(id).orElse(new User());
    }
}
