package com.example.testloginapi.domain.user.service;

import com.example.testloginapi.domain.user.domain.dto.GoogleLoginRequestDto;
import com.example.testloginapi.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void save(GoogleLoginRequestDto requestDto) {
        userRepository.save(requestDto.toEntity());
    }
}
