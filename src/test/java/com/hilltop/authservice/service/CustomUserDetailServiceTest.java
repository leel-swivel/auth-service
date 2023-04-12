package com.hilltop.authservice.service;

import com.hilltop.authservice.entity.UserCredential;
import com.hilltop.authservice.repository.UserCredentialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CustomUserDetailServiceTest {
    @Mock
    private UserCredentialRepository userCredentialRepository;

    @Mock
    private CustomUserDetailService customUserDetailService;


    @BeforeEach
    void setUp() {
        openMocks(this);

    }


    @Test
    void testLoadUserByUsernameWithValidUsername() {


        UserCredential userCredential = new UserCredential("id", "user", "email.com", "pass");

        when(userCredentialRepository.findByName(userCredential.getName())).thenReturn(Optional.of(userCredential));

        var userDetails = userCredentialRepository.findByName(userCredential.getName());

        assertEquals("user", userDetails.get().getName());

    }

}