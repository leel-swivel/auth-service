package com.hilltop.authservice.service;

import com.hilltop.authservice.domain.request.UserCredentialRequestDto;
import com.hilltop.authservice.exception.AuthServiceException;
import com.hilltop.authservice.exception.InvalidAccessException;
import com.hilltop.authservice.repository.UserCredentialRepository;
import com.hilltop.authservice.utill.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class AuthServiceTest {

    private AuthService authService;
    @Mock
    private UserCredentialRepository userCredentialRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;


    @BeforeEach
    void setUp() {
        openMocks(this);
        authService = new AuthService(userCredentialRepository, passwordEncoder, jwtUtil);
    }

    @Test
    void Should_SaveUserDetailOnDatabase_When_ValidDataIsGiven() {
        var userCredential = getSampleUserCredential();
        authService.saveUser(userCredential);
        verify(userCredentialRepository, times(1)).save(any());
    }

    @Test
    void Should_ThrowAuthServiceException_When_FailedToAddUserData() {
        var userCredential = getSampleUserCredential();
        when(userCredentialRepository.save(any())).thenThrow(new DataAccessException("FAILED") {
        });
        AuthServiceException authServiceException = assertThrows(AuthServiceException.class,
                () -> authService.saveUser(userCredential));
        assertEquals("Saving user info into database was failed.", authServiceException.getMessage());
    }


    @Test
    void Should_CallJwtTokenServiceGenerateTokenMethod_When_UserNameIsGiving() {
        authService.generateToken("leel");
        verify(jwtUtil, times(1)).generateToken("leel");
    }

    @Test
    void Should_ThrowInvalidAccessException_When_GenerateAToken() {
        when(jwtUtil.generateToken("leel")).thenThrow(new DataAccessException("ERROR") {
        });
        InvalidAccessException invalidAccessException = assertThrows(InvalidAccessException.class,
                () -> authService.generateToken("leel"));
        assertEquals("Invalid access.", invalidAccessException.getMessage());
    }

    @Test
    void Should_CallJwtTokenServiceValidateTokenMethod_When_TokenIsGiving() {
        String token = authService.generateToken("leel");
        authService.validateToken(token);
        verify(jwtUtil, times(1)).validateToken(token);
    }

    private UserCredentialRequestDto getSampleUserCredential() {
        return new UserCredentialRequestDto("leel", "le@gmail.com", "pass");
    }

}