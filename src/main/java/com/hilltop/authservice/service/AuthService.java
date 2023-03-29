package com.hilltop.authservice.service;

import com.hilltop.authservice.domain.request.UserCredentialRequestDto;
import com.hilltop.authservice.entity.UserCredential;
import com.hilltop.authservice.exception.AuthServiceException;
import com.hilltop.authservice.exception.InvalidAccessException;
import com.hilltop.authservice.repository.UserCredentialRepository;
import com.hilltop.authservice.utill.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserCredentialRepository userCredentialRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userCredentialRepository = userCredentialRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String saveUser(UserCredentialRequestDto userCredentialRequestDto) {
        try {
            userCredentialRequestDto.setPassword(passwordEncoder.encode(userCredentialRequestDto.getPassword()));
            UserCredential userCredential = new UserCredential(userCredentialRequestDto);
            userCredentialRepository.save(userCredential);
            log.info("Successfully created the user: {}", userCredential.getId());
            return userCredential.getId();
        } catch (DataAccessException e) {
            log.error("Error saving user due to :{}", e.toString());
            throw new AuthServiceException("Saving user info into database was failed.", e);
        }
    }

    public String generateToken(String userName) {
        try {
            log.info("Generating token for user name: {}", userName);
            return jwtUtil.generateToken(userName);
        } catch (DataAccessException e) {
            throw new InvalidAccessException("Invalid access.");
        }
    }

    public void validateToken(String token) {
        try {
            log.info("Token validation started.");
            jwtUtil.validateToken(token);
        } catch (DataAccessException e) {
            throw new AuthServiceException("Invalid access.");
        }
    }
}
