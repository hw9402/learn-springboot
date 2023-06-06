package com.example.testloginapi.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/login/google")
    public void test(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        // 기대값: 구글로부터 받은 엑세스 토큰
        System.out.println(token);
    }
}
