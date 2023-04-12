package com.hilltop.authservice.utill;

import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtUtilTest {
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    void ValidateToken_ShouldThrowInvalidTokenException_WhenTokenIsInvalid() {
        String invalidToken = "invalid.token";

        assertThrows(MalformedJwtException.class, () -> jwtUtil.validateToken(invalidToken));
    }

    @Test
    void ValidateToken_ShouldNotThrowAnyException_WhenTokenIsValid() {
        String validToken = jwtUtil.generateToken("user123");

        assertDoesNotThrow(() -> jwtUtil.validateToken(validToken));
    }

}