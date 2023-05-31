package com.example.testloginapi.domain.user.domain.dto;

import com.example.testloginapi.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleLoginRequestDto {

    private String providerId;
    private String name;
    private String email;
    private String imageUrl;

    @Builder
    public GoogleLoginRequestDto(String providerId, String name, String email, String imageUrl) {
        this.providerId = providerId;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public User toEntity() {
        return User.builder()
                .providerId(this.providerId)
                .name(this.name)
                .email(this.email)
                .imageUrl(this.imageUrl)
                .build();
    }
}
