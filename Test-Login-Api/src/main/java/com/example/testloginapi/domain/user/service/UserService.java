package com.example.testloginapi.domain.user.service;

import com.example.testloginapi.domain.auth.domain.dto.GoogleResponseTokenDto;
import com.example.testloginapi.domain.auth.domain.dto.GoogleResponseUserInfoDto;
import com.example.testloginapi.domain.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

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

    public void findOne(Long id) {
        System.out.println(userRepository.findById(id));
    }
}
