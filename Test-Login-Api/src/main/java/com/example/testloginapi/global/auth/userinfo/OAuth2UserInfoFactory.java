package com.example.testloginapi.global.auth.userinfo;

import com.example.testloginapi.domain.user.domain.AuthProvider;

import java.util.Map;

import static org.springframework.security.config.oauth2.client.CommonOAuth2Provider.GOOGLE;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(AuthProvider authProvider, Map<String, Object> attributes) {
        switch (authProvider) {
            case GOOGLE -> {
                return new GoogleOAuth2UserInfo(attributes);
            }
        }
        throw new IllegalArgumentException("Invalid Provider Type.");
    }
}
