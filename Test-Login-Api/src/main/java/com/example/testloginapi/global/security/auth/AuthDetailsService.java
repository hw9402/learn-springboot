package com.example.testloginapi.global.security.auth;

import com.example.testloginapi.domain.user.domain.User;
import com.example.testloginapi.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return new AuthDetails(user.get());
        } else {
            throw new UsernameNotFoundException("유저를 찾을 수 없습니다.");
        }
    }
}
