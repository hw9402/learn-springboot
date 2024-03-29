package com.example.testloginapi.domain.auth.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleResponseTokenDto {

    private String access_token;
    private Long expires_in;
    private String refresh_token;
    private String scope;
    private String token_type;
    private String id_token;

    @Builder
    public GoogleResponseTokenDto(String access_token, Long expires_in, String refresh_token, String scope, String token_type, String id_token) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
        this.scope = scope;
        this.token_type = token_type;
        this.id_token = id_token;
    }
}