package com.example.testloginapi.domain.auth.service.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class GoogleOAuthRedirectUrl {

    private final GoogleProperties googleProperties;

    public String getOAuthRedirectUrl() {
        Map<String, Object> param = new HashMap<>();
        param.put("response_type", "code");
        param.put("access_type", "offline");
        param.put("client_id", googleProperties.getClientId());
        param.put("redirect_uri", googleProperties.getRedirectUri());
        param.put("scope", googleProperties.getScope());

        String queryString = param.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        String redirectUrl = googleProperties.getGoogleLoginEndpoint() + "?" + queryString;
        System.out.println("redirect url : " + redirectUrl);

        return redirectUrl;
    }
}
