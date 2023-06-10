package com.example.testloginapi.domain.user.service;

import com.example.testloginapi.domain.auth.domain.dto.GoogleResponseTokenDto;
import com.example.testloginapi.domain.auth.domain.dto.GoogleResponseUserInfoDto;
import com.example.testloginapi.domain.user.domain.User;
import com.example.testloginapi.domain.user.domain.dto.UserDto;
import com.example.testloginapi.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ObjectMapper mapper;

    public String save(String tokenInfo) throws IOException {
        GoogleResponseTokenDto dto = mapper.readValue(tokenInfo, GoogleResponseTokenDto.class);

        Base64.Decoder decoder = Base64.getUrlDecoder();
        byte[] decodedToken = decoder.decode(dto.getId_token().split("\\.")[1].getBytes());

        GoogleResponseUserInfoDto userInfoDto = mapper.readValue(decodedToken, GoogleResponseUserInfoDto.class);

        userRepository.save(userInfoDto.toEntity());

        return userInfoDto.getEmail();
    }

    public UserDto findOne(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return UserDto.builder()
                    .providerId(user.get().getProviderId())
                    .email(user.get().getEmail())
                    .name(user.get().getName())
                    .build();
        } else {
            System.out.println(id + "번 유저는 존재하지 않습니다.");
            return new UserDto();
        }
    }
}
