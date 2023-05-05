package com.example.testloginapi.domain.account;

import com.example.testloginapi.global.exception.BadRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AccountResponse signUp(SignUpRequest signUpRequest) {
        boolean isExist = accountRepository
                .existsByEmail(signUpRequest.getEmail());
        if (isExist) throw new BadRequestException("이미 존재하는 이메일입니다.");
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        Account account = new Account(
                signUpRequest.getEmail(),
                encodedPassword,
                signUpRequest.getNickname());

        account = accountRepository.save(account);
        return AccountResponse.of(account);
    }
}