package com.hexagonal.notice.infrastructure.security;

import com.hexagonal.notice.infrastructure.config.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtUtil, "secret", "testSecretKeyForJWTTokenGenerationThatShouldBeVeryLongAndSecure");
        ReflectionTestUtils.setField(jwtUtil, "expiration", 86400000L); // 24 hours

        userDetails = User.builder()
                .username("testuser")
                .password("password")
                .authorities("ROLE_USER")
                .build();
    }

    @Test
    @DisplayName("Should generate valid JWT token for user details")
    void generateToken_shouldGenerateValidToken_whenUserDetailsProvided() {
        String token = jwtUtil.generateToken(userDetails);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.split("\\.").length == 3);
    }

    @Test
    @DisplayName("Should extract username from valid JWT token")
    void extractUsername_shouldReturnUsername_whenValidTokenProvided() {
        String token = jwtUtil.generateToken(userDetails);

        String extractedUsername = jwtUtil.extractUsername(token);

        assertEquals("testuser", extractedUsername);
    }

    @Test
    @DisplayName("Should extract expiration date from JWT token")
    void extractExpiration_shouldReturnExpirationDate_whenValidTokenProvided() {
        String token = jwtUtil.generateToken(userDetails);

        Date expiration = jwtUtil.extractExpiration(token);

        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    @DisplayName("Should validate token successfully for correct user details")
    void validateToken_shouldReturnTrue_whenTokenAndUserDetailsMatch() {
        String token = jwtUtil.generateToken(userDetails);

        Boolean isValid = jwtUtil.validateToken(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should invalidate token for different user details")
    void validateToken_shouldReturnFalse_whenTokenAndUserDetailsDoNotMatch() {
        String token = jwtUtil.generateToken(userDetails);
        UserDetails differentUser = User.builder()
                .username("differentuser")
                .password("password")
                .authorities("ROLE_USER")
                .build();

        Boolean isValid = jwtUtil.validateToken(token, differentUser);

        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should throw exception for invalid token")
    void extractUsername_shouldThrowException_whenInvalidTokenProvided() {
        String invalidToken = "invalid.token.here";

        assertThrows(Exception.class, () -> jwtUtil.extractUsername(invalidToken));
    }
}
