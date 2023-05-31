package com.example.testloginapi.domain.user.controller;

import com.example.testloginapi.domain.user.domain.dto.GoogleLoginRequestDto;
import com.example.testloginapi.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CrossOrigin("http://localhost:3000")
    @PostMapping("/api/v1/user")
    public void save(@RequestBody GoogleLoginRequestDto requestDto) {
        userService.save(requestDto);
    }
}
