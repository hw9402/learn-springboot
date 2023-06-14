package com.example.testloginapi.domain.auth.service.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class GoogleProperties {

    @Value("${oauth2.google.url}")
    private String googleLoginEndpoint;

    @Value("${oauth2.google.get-access-token}")
    private String googleGetAccessTokenEndpoint;

    @Value("${oauth2.google.client-id}")
    private String clientId;

    @Value("${oauth2.google.client-secret}")
    private String clientSecret;

    @Value("${oauth2.google.redirect-uri}")
    private String redirectUri;

    @Value("${oauth2.google.resource-uri}")
    private String resourceUri;

    @Value("${oauth2.google.scope}")
    private String scope;
}
