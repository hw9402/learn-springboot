package com.example.testloginapi.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ROLE_ADMIN("admin"),
    ROLE_USER("user"),
    ROLE_GUEST("guest");

    private final String key;
}
