package com.example.testloginapi.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoogleOAuthService {

    @Value("${app.google.url}")
    private String googleLoginEndpoint;

    @Value("${app.google.get-access-token}")
    private String googleGetAccessTokenEndpoint;

    @Value("${app.google.client-id}")
    private String clientId;

    @Value("${app.google.client-secret}")
    private String clientSecret;

    @Value("${app.google.redirect-uri}")
    private String redirectUri;

    @Value("${app.google.scope}")
    private String scope;

    public String getOAuthRedirectUrl() {
        Map<String, Object> param = new HashMap<>();
        param.put("response_type", "code");
        param.put("access_type", "offline");
        param.put("client_id", clientId);
        param.put("redirect_uri", redirectUri);
        param.put("scope", scope);

        String queryString = param.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        String redirectUrl = googleLoginEndpoint + "?" + queryString;
        System.out.println("redirect url : " + redirectUrl);

        return redirectUrl;
    }

    public String tokenInfo(String code) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("code", code);
        param.add("client_id", clientId);
        param.add("client_secret", clientSecret);
        param.add("redirect_uri", redirectUri);
        param.add("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(googleGetAccessTokenEndpoint, param, String.class);

        return responseEntity.getBody();
    }
}
