package com.example.testloginapi.domain.auth.domain.dto;

import com.example.testloginapi.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleResponseUserInfoDto {

    private String iss;
    private String azp;
    private String aud;
    private String sub;
    private String email;
    private String email_verified;
    private String at_hash;
    private String name;
    private String picture;
    private String given_name;
    private String locale;
    private String iat;
    private String exp;

    @Builder

    public GoogleResponseUserInfoDto(String iss, String azp, String aud, String sub,
                                     String email, String email_verified, String at_hash,
                                     String name, String picture, String given_name,
                                     String locale, String iat, String exp) {
        this.iss = iss;
        this.azp = azp;
        this.aud = aud;
        this.sub = sub;
        this.email = email;
        this.email_verified = email_verified;
        this.at_hash = at_hash;
        this.name = name;
        this.picture = picture;
        this.given_name = given_name;
        this.locale = locale;
        this.iat = iat;
        this.exp = exp;
    }

    public User toEntity() {
        return User.builder()
                .providerId(sub)
                .email(email)
                .name(name)
                .build();
    }
}
