package com.hilltop.authservice.config;

import com.hilltop.authservice.service.CustomUserDetailService;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class AuthConfigTest {


    @Test
    void userDetailsServiceShouldReturnCustomUserDetailService() {
        AuthConfig authConfig = new AuthConfig();
        UserDetailsService userDetailsService = authConfig.userDetailsService();
        assertNotNull(userDetailsService);
        assertTrue(userDetailsService instanceof CustomUserDetailService);
    }

    @Test
    void passwordEncoderShouldReturnBCryptPasswordEncoder() {
        AuthConfig authConfig = new AuthConfig();
        PasswordEncoder passwordEncoder = authConfig.passwordEncoder();
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder, "PasswordEncoder should be an instance of BCryptPasswordEncoder.");
    }


    @Test
    void authenticationProviderShouldReturnDaoAuthenticationProvider() {
        AuthConfig authConfig = new AuthConfig();
        AuthenticationProvider authenticationProvider = authConfig.authenticationProvider();
        assertNotNull(authenticationProvider);
        assertTrue(authenticationProvider instanceof DaoAuthenticationProvider);
    }


    @Test
    void authenticationManagerShouldReturnAuthenticationManager() throws Exception {
        AuthConfig authConfig = new AuthConfig();
        AuthenticationConfiguration config = mock(AuthenticationConfiguration.class);
        AuthenticationManager authenticationManager = authConfig.authenticationManager(config);
        assertEquals(authenticationManager, config.getAuthenticationManager());
    }
}

