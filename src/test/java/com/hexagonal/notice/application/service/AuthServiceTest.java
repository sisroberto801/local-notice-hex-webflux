package com.hexagonal.notice.application.service;

import com.hexagonal.notice.domain.model.AuthenticationCommand;
import com.hexagonal.notice.domain.model.AuthenticationResult;
import com.hexagonal.notice.domain.port.in.user.RetrieveUserUseCase;
import com.hexagonal.notice.domain.model.User;
import com.hexagonal.notice.infrastructure.config.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
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
    private RetrieveUserUseCase retrieveUserUseCase;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    private AuthenticationCommand validCommand;
    private AuthenticationCommand invalidCommand;
    private UserDetails userDetails;
    private User user;

    @BeforeEach
    void setUp() {
        validCommand = AuthenticationCommand.builder()
                .username("testuser")
                .password("password123")
                .build();

        invalidCommand = AuthenticationCommand.builder()
                .username("testuser")
                .password("wrongpassword")
                .build();

        user = User.builder()
                .id(1L)
                .username("testuser")
                .password("password123")
                .status(true)
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
        when(retrieveUserUseCase.getUserByUsername("testuser")).thenReturn(Mono.just(user));
        when(jwtUtil.generateToken(userDetails)).thenReturn("mock.jwt.token");
        when(authenticationManager.authenticate(any())).thenReturn(Mono.empty());

        AuthenticationResult result = authService.authenticate(validCommand).block();

        assertNotNull(result);
        assertEquals("mock.jwt.token", result.getToken());

        verify(authenticationManager).authenticate(any());
        verify(retrieveUserUseCase).getUserByUsername("testuser");
        verify(jwtUtil).generateToken(userDetails);
    }

    @Test
    @DisplayName("Should throw exception when authentication fails")
    void authenticate_shouldThrowException_whenInvalidCredentialsProvided() {
        when(authenticationManager.authenticate(any())).thenReturn(Mono.error(new BadCredentialsException("Invalid credentials")));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.authenticate(invalidCommand).block());

        assertEquals("Invalid credentials", exception.getMessage());

        verify(authenticationManager).authenticate(any());
        verify(retrieveUserUseCase, never()).getUserByUsername(any());
        verify(jwtUtil, never()).generateToken(any());
    }

    @Test
    @DisplayName("Should handle null username in authentication command")
    void authenticate_shouldThrowException_whenNullUsernameProvided() {
        AuthenticationCommand commandWithNullUsername = AuthenticationCommand.builder()
                .username(null)
                .password("password123")
                .build();

        assertThrows(Exception.class,
                () -> authService.authenticate(commandWithNullUsername).block());

        verify(authenticationManager, never()).authenticate(any());
    }

    @Test
    @DisplayName("Should handle null password in authentication command")
    void authenticate_shouldThrowException_whenNullPasswordProvided() {
        AuthenticationCommand commandWithNullPassword = AuthenticationCommand.builder()
                .username("testuser")
                .password(null)
                .build();

        assertThrows(Exception.class,
                () -> authService.authenticate(commandWithNullPassword).block());

        verify(authenticationManager, never()).authenticate(any());
    }

    @Test
    @DisplayName("Should handle empty username in authentication command")
    void authenticate_shouldThrowException_whenEmptyUsernameProvided() {
        AuthenticationCommand commandWithEmptyUsername = AuthenticationCommand.builder()
                .username("")
                .password("password123")
                .build();

        assertThrows(Exception.class,
                () -> authService.authenticate(commandWithEmptyUsername).block());

        verify(authenticationManager, never()).authenticate(any());
    }

    @Test
    @DisplayName("Should handle empty password in authentication command")
    void authenticate_shouldThrowException_whenEmptyPasswordProvided() {
        AuthenticationCommand commandWithEmptyPassword = AuthenticationCommand.builder()
                .username("testuser")
                .password("")
                .build();

        assertThrows(Exception.class,
                () -> authService.authenticate(commandWithEmptyPassword).block());

        verify(authenticationManager, never()).authenticate(any());
    }
}
