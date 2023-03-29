package com.hilltop.authservice.service;

import com.hilltop.authservice.domain.request.UserCredentialRequestDto;
import com.hilltop.authservice.entity.UserCredential;
import com.hilltop.authservice.repository.UserCredentialRepository;
import com.hilltop.authservice.utill.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserCredentialRepository userCredentialRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userCredentialRepository = userCredentialRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    //TODO add try catch and logs
    public String saveUser(UserCredentialRequestDto userCredentialRequestDto) {
        userCredentialRequestDto.setPassword(passwordEncoder.encode(userCredentialRequestDto.getPassword()));
        UserCredential userCredential = new UserCredential(userCredentialRequestDto);
        userCredentialRepository.save(userCredential);
        return "user saved";
    }


    public String generateToken(String userName) {
        return jwtUtil.generateToken(userName);
    }


    public void validateToken(String token) {
        jwtUtil.validateToken(token);
    }
}
