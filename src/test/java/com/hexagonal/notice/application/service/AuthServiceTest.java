package com.hexagonal.notice.application.service;

import com.hexagonal.notice.infrastructure.config.JwtUtil;
import com.hexagonal.notice.infrastructure.dto.AuthenticationRequest;
import com.hexagonal.notice.infrastructure.dto.AuthenticationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private ReactiveAuthenticationManager authenticationManager;

    @Mock
    private ReactiveUserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    private AuthenticationRequest validRequest;
    private AuthenticationRequest invalidRequest;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        validRequest = AuthenticationRequest.builder()
                .username("testuser")
                .password("password123")
                .build();

        invalidRequest = AuthenticationRequest.builder()
                .username("testuser")
                .password("wrongpassword")
                .build();

        userDetails = org.springframework.security.core.userdetails.User.builder()
                .username("testuser")
                .password("password123")
                .authorities("ROLE_USER")
                .build();
    }

    @Test
    @DisplayName("Should authenticate successfully and return JWT token")
    void authenticate_shouldReturnToken_whenValidCredentialsProvided() {
        when(userDetailsService.findByUsername("testuser")).thenReturn(Mono.just(userDetails));
        when(jwtUtil.generateToken(userDetails)).thenReturn("mock.jwt.token");
        when(authenticationManager.authenticate(any())).thenReturn(Mono.empty());

        AuthenticationResponse response = authService.authenticate(validRequest).block();

        assertNotNull(response);
        assertEquals("mock.jwt.token", response.getToken());

        verify(authenticationManager).authenticate(any());
        verify(userDetailsService).findByUsername("testuser");
        verify(jwtUtil).generateToken(userDetails);
    }

    @Test
    @DisplayName("Should throw exception when authentication fails")
    void authenticate_shouldThrowException_whenInvalidCredentialsProvided() {
        when(authenticationManager.authenticate(any())).thenReturn(Mono.error(new BadCredentialsException("Invalid credentials")));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.authenticate(invalidRequest).block());

        assertEquals("Invalid credentials", exception.getMessage());

        verify(authenticationManager).authenticate(any());
        verify(userDetailsService, never()).findByUsername(any());
        verify(jwtUtil, never()).generateToken(any());
    }

    @Test
    @DisplayName("Should handle null username in authentication request")
    void authenticate_shouldThrowException_whenNullUsernameProvided() {
        AuthenticationRequest requestWithNullUsername = AuthenticationRequest.builder()
                .username(null)
                .password("password123")
                .build();

        assertThrows(Exception.class,
                () -> authService.authenticate(requestWithNullUsername).block());

        verify(authenticationManager, never()).authenticate(any());
    }

    @Test
    @DisplayName("Should handle null password in authentication request")
    void authenticate_shouldThrowException_whenNullPasswordProvided() {
        AuthenticationRequest requestWithNullPassword = AuthenticationRequest.builder()
                .username("testuser")
                .password(null)
                .build();

        assertThrows(Exception.class,
                () -> authService.authenticate(requestWithNullPassword).block());

        verify(authenticationManager, never()).authenticate(any());
    }

    @Test
    @DisplayName("Should handle empty username in authentication request")
    void authenticate_shouldThrowException_whenEmptyUsernameProvided() {
        AuthenticationRequest requestWithEmptyUsername = AuthenticationRequest.builder()
                .username("")
                .password("password123")
                .build();

        assertThrows(Exception.class,
                () -> authService.authenticate(requestWithEmptyUsername).block());

        verify(authenticationManager, never()).authenticate(any());
    }

    @Test
    @DisplayName("Should handle empty password in authentication request")
    void authenticate_shouldThrowException_whenEmptyPasswordProvided() {
        AuthenticationRequest requestWithEmptyPassword = AuthenticationRequest.builder()
                .username("testuser")
                .password("")
                .build();

        assertThrows(Exception.class,
                () -> authService.authenticate(requestWithEmptyPassword).block());

        verify(authenticationManager, never()).authenticate(any());
    }
}
