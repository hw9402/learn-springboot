package com.example.testloginapi.domain.auth.service;

import com.example.testloginapi.domain.auth.controller.dto.GoogleResponseTokenDto;
import com.example.testloginapi.domain.auth.controller.dto.GoogleResponseUserInfoDto;
import com.example.testloginapi.domain.auth.service.util.GoogleProperties;
import com.example.testloginapi.domain.user.domain.Provider;
import com.example.testloginapi.domain.user.domain.Role;
import com.example.testloginapi.domain.user.domain.User;
import com.example.testloginapi.domain.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final GoogleProperties googleProperties;
    private final UserRepository userRepository;

    public void login(String code, String registrationId) throws JsonProcessingException {
        String accessToken = getAccessToken(code, registrationId);
        GoogleResponseUserInfoDto userResource = getUserResource(accessToken, registrationId);

        // 유저가 존재하지 않는다면 회원가입 아니면 변경된 점 업데이트(이름, 프로필 사진)
        if (userRepository.findByEmail(userResource.getEmail()).isEmpty()) {
            save(userResource);
        } else {
            update(userResource);
        }
    }

    private String getAccessToken(String authorizationCode, String registrationId) throws JsonProcessingException {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("code", authorizationCode);
        param.add("client_id", googleProperties.getClientId());
        param.add("client_secret", googleProperties.getClientSecret());
        param.add("redirect_uri", googleProperties.getRedirectUri());
        param.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(param, headers);

        ResponseEntity<GoogleResponseTokenDto> responseEntity = restTemplate.exchange(googleProperties.getGoogleGetAccessTokenEndpoint(), HttpMethod.POST, entity, GoogleResponseTokenDto.class);

        return responseEntity.getBody().getAccess_token();
    }

    private GoogleResponseUserInfoDto getUserResource(String accessToken, String registrationId) {
        String resourceUri = googleProperties.getResourceUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<GoogleResponseUserInfoDto> responseEntity = restTemplate.exchange(resourceUri, HttpMethod.GET, entity, GoogleResponseUserInfoDto.class);

        return responseEntity.getBody();
    }

    @Transactional
    public void save(GoogleResponseUserInfoDto userResource) {
        User user = User.builder()
                .providerId(userResource.getId())
                .email(userResource.getEmail())
                .name(userResource.getName())
                .profileUrl(userResource.getPicture())
                .role(Role.ROLE_USER)
                .provider(Provider.GOOGLE)
                .build();
        userRepository.save(user);
    }

    @Transactional
    public void update(GoogleResponseUserInfoDto userResource) {
        User user = userRepository.findByEmail(userResource.getEmail()).orElse(new User());
        user.updateUser(userResource);
    }
}