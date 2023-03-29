package com.hilltop.authservice.controller;

import com.hilltop.authservice.config.Translator;
import com.hilltop.authservice.domain.request.UserCredentialRequestDto;
import com.hilltop.authservice.domain.request.UserLoginRequestDto;
import com.hilltop.authservice.domain.response.TokenResponseDto;
import com.hilltop.authservice.domain.response.UserRegisterResponseDto;
import com.hilltop.authservice.enums.ErrorResponseStatusType;
import com.hilltop.authservice.enums.SuccessResponseStatusType;
import com.hilltop.authservice.exception.AuthServiceException;
import com.hilltop.authservice.exception.InvalidAccessException;
import com.hilltop.authservice.exception.InvalidTokenException;
import com.hilltop.authservice.service.AuthService;
import com.hilltop.authservice.wrapper.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController extends Controller {

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    public AuthController(Translator translator, AuthService authService, AuthenticationManager authenticationManager) {
        super(translator);
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/register")
    public ResponseEntity<ResponseWrapper> addNewUser(@RequestBody UserCredentialRequestDto user) {
        try {
            String userId = authService.saveUser(user);
            var userRegisterResponseDto = new UserRegisterResponseDto(userId);
            log.info("Successfully register the user : {}", userId);
            return getSuccessResponse(userRegisterResponseDto, SuccessResponseStatusType.CREATE_USER, HttpStatus.CREATED);
        } catch (AuthServiceException e) {
            log.error("Saving user details was failed.", e);
            return getInternalServerError();
        }
    }


    @PostMapping("/token")
    public ResponseEntity<ResponseWrapper> getToken(@RequestBody UserLoginRequestDto userLoginRequestDto) {

        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            userLoginRequestDto.getUserName(), userLoginRequestDto.getPassword()));
            if (authenticate.isAuthenticated()) {
                String token = authService.generateToken(userLoginRequestDto.getUserName());
                var tokenResponseDto = new TokenResponseDto(token);
                return getSuccessResponse(tokenResponseDto, SuccessResponseStatusType.GENERATE_TOKEN, HttpStatus.OK);
            } else {
                throw new InvalidAccessException("Invalid access");
            }
        } catch (InvalidAccessException e) {
            log.error("Invalid user credentials.");
            return getErrorResponse(ErrorResponseStatusType.INVALID_ACCESS);
        } catch (AuthServiceException e) {
            return getInternalServerError();
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<ResponseWrapper> validateToken(@RequestParam("token") String token) {
        try {
            authService.validateToken(token);
            return getSuccessResponse(null, SuccessResponseStatusType.VALIDATE_TOKEN, HttpStatus.OK);
        } catch (InvalidTokenException e) {
            log.error("Invalid token: {}", token);
            return getErrorResponse(ErrorResponseStatusType.INVALID_TOKEN);
        } catch (AuthServiceException e) {
            log.error("Invalid token.", e);
            return getInternalServerError();
        }
    }
}
