package com.hexagonal.notice.application.service;

import com.hexagonal.notice.domain.model.AuthenticationCommand;
import com.hexagonal.notice.domain.model.AuthenticationResult;
import com.hexagonal.notice.domain.port.in.auth.AuthenticateUserUseCase;
import com.hexagonal.notice.domain.port.in.user.RetrieveUserUseCase;
import com.hexagonal.notice.infrastructure.config.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Slf4j
@Service
public class AuthService implements AuthenticateUserUseCase {

    private final ReactiveAuthenticationManager authenticationManager;
    private final RetrieveUserUseCase retrieveUserUseCase;
    private final JwtUtil jwtUtil;

    public AuthService(
            ReactiveAuthenticationManager authenticationManager,
            @Qualifier("retrieveUserBean") RetrieveUserUseCase retrieveUserUseCase,
            JwtUtil jwtUtil
    ) {
        this.authenticationManager = authenticationManager;
        this.retrieveUserUseCase = retrieveUserUseCase;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<AuthenticationResult> authenticate(AuthenticationCommand command) {
        log.info("Attempting to authenticate user: {}", command.getUsername());

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        command.getUsername(),
                        command.getPassword()
                )
        ).flatMap(auth -> {
            return retrieveUserUseCase.getUserByUsername(command.getUsername())
                    .map(user -> {
                        try {
                            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                                    .username(user.getUsername())
                                    .password(user.getPassword())
                                    .authorities(Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER")))
                                    .build();

                            final String jwt = jwtUtil.generateToken(userDetails);

                            log.info("User authenticated successfully: {}", command.getUsername());

                            return AuthenticationResult.builder()
                                    .token(jwt)
                                    .build();
                        } catch (Exception e) {
                            log.error("Error generating JWT token for user: {}", command.getUsername(), e);
                            throw new RuntimeException("Error generating token");
                        }
                    });
        }).onErrorResume(e -> {
            log.error("Authentication failed for user: {}", command.getUsername(), e);
            return Mono.error(new RuntimeException("Invalid credentials"));
        });
    }
}
