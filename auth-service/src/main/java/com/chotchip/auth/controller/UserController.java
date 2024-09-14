package com.chotchip.auth.controller;

import com.chotchip.auth.dto.SignInRequest;
import com.chotchip.auth.dto.SignUpRequest;
import com.chotchip.auth.entity.User;
import com.chotchip.auth.service.JWTService;
import com.chotchip.auth.service.UserDetailService;
import com.chotchip.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/jwt")
@RequiredArgsConstructor
public class UserController {


    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailService userDetailService;


    @PostMapping("/register/user")
    public User createUser(@RequestBody SignUpRequest signUpRequest) {
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return userService.createUser(signUpRequest);
    }

    @PostMapping("/auth")
    public String auth(@RequestBody SignInRequest signInRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(), signInRequest.getPassword()
        ));
        if (authenticate.isAuthenticated())
            return jwtService.generateToken(userDetailService.loadUserByUsername(signInRequest.getUsername()));
        else
            throw new UsernameNotFoundException("Invalid credentials");
    }
}
