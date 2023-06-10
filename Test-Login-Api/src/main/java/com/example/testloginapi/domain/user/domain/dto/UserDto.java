package com.example.testloginapi.domain.user.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {

    private String providerId;
    private String email;
    private String name;

    @Builder
    public UserDto(String providerId, String email, String name) {
        this.providerId = providerId;
        this.email = email;
        this.name = name;
    }
}
