package com.hilltop.authservice.controller;

import com.hilltop.authservice.config.Translator;
import com.hilltop.authservice.domain.request.UserCredentialRequestDto;
import com.hilltop.authservice.domain.request.UserLoginRequestDto;
import com.hilltop.authservice.exception.AuthServiceException;
import com.hilltop.authservice.exception.InvalidTokenException;
import com.hilltop.authservice.service.AuthService;
import com.hilltop.authservice.wrapper.ResponseWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {

    private static final String REGISTER_URL = "/api/v1/auth/register";
    private static final String GET_TOKEN_URL = "/api/v1/auth/token";
    private static final String VALIDATE_TOKEN_URL = "/api/v1/auth/validate";

    @Mock
    private AuthService authService;
    @Mock
    private Translator translator;
    @Mock
    private AuthenticationManager authenticationManager;
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        initMocks(this);
        AuthController authController = new AuthController(translator, authService, authenticationManager);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void Should_ReturnOk_When_RegisterUser() throws Exception {
        UserCredentialRequestDto sampleUserCredentialRequestDto = getSampleUserCredentialRequestDto();

        mockMvc.perform(MockMvcRequestBuilders.post(REGISTER_URL)
                        .content(sampleUserCredentialRequestDto.toLogJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }


    @Test
    void Should_Return_InternalServerError_When_RegisterAUser() throws Exception {
        UserCredentialRequestDto sampleUserCredentialRequestDto = getSampleUserCredentialRequestDto();
        doThrow(new AuthServiceException("ERROR")).when(authService).saveUser(any());
        mockMvc.perform(MockMvcRequestBuilders.post(REGISTER_URL)
                        .content(sampleUserCredentialRequestDto.toLogJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }


    @Test
    void Should_Return_Ok_When_GetToken() throws Exception {
        UserLoginRequestDto sampleUserLoginRequestDto = getSampleUserLoginRequestDto();
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authService.generateToken("username")).thenReturn("token");
        mockMvc.perform(MockMvcRequestBuilders.post(GET_TOKEN_URL)
                        .content(sampleUserLoginRequestDto.toLogJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void Should_Return_BadRequest_When_BadCredentialsGiven() throws Exception {
        UserLoginRequestDto sampleUserLoginRequestDto = getSampleUserLoginRequestDto();

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post(GET_TOKEN_URL)
                        .content(sampleUserLoginRequestDto.toLogJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    void Should_Return_Ok_When_ValidatingAToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(VALIDATE_TOKEN_URL)
                        .param("token", "TOKEN")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void Should_Return_InvalidTokenException_When_ValidatingAToken() throws Exception {
        UserCredentialRequestDto sampleUserCredentialRequestDto = getSampleUserCredentialRequestDto();
        doThrow(new InvalidTokenException("ERROR")).when(authService).validateToken(any());
        mockMvc.perform(MockMvcRequestBuilders.get(VALIDATE_TOKEN_URL)
                        .param("token", "TOKEN")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void Should_Return_AuthServiceException_When_ValidatingAToken() throws Exception {
        UserCredentialRequestDto sampleUserCredentialRequestDto = getSampleUserCredentialRequestDto();
        doThrow(new AuthServiceException("ERROR")).when(authService).validateToken(any());
        mockMvc.perform(MockMvcRequestBuilders.get(VALIDATE_TOKEN_URL)
                        .param("token", "TOKEN")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    private UserLoginRequestDto getSampleUserLoginRequestDto() {
        return new UserLoginRequestDto("user", "pass");
    }


    private UserCredentialRequestDto getSampleUserCredentialRequestDto() {
        return new UserCredentialRequestDto("leel", "leel@leel.com", "leel");
    }


}