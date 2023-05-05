package com.example.testloginapi.domain.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class SignUpRequest {

    private final String email;

    private final String password;

    private final String nickname;
}