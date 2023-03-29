package com.hilltop.authservice.controller;

import com.hilltop.authservice.domain.request.UserCredentialRequestDto;
import com.hilltop.authservice.domain.request.UserLoginRequestDto;
import com.hilltop.authservice.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredentialRequestDto user) {
        return authService.saveUser(user);
    }


    @PostMapping("/token")
    public String getToken(@RequestBody UserLoginRequestDto userLoginRequestDto) {

        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userLoginRequestDto.getUserName(), userLoginRequestDto.getPassword()));
        if (authenticate.isAuthenticated()) {
            return authService.generateToken(userLoginRequestDto.getUserName());
        } else {
            throw new RuntimeException("Invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "Token is valid";
    }
}
