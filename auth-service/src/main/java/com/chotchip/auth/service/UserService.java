package com.chotchip.auth.service;

import com.chotchip.auth.dto.SignUpRequest;
import com.chotchip.auth.entity.User;
import com.chotchip.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional()
    public User createUser(SignUpRequest signUpRequest) {
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(signUpRequest.getPassword())
                .email(signUpRequest.getEmail())
                .build();
        return userRepository.save(user);
    }


}
