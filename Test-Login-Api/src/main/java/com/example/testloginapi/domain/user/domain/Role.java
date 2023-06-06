package com.example.testloginapi.domain.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ROLE_GUEST("guest"),
    ROLE_USER("user"),
    ROLE_ADMIN("admin");

    private final String key;
}
