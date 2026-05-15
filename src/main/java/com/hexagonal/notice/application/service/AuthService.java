package com.hexagonal.notice.application.service;

import com.hexagonal.notice.domain.model.AuthenticationCommand;
import com.hexagonal.notice.domain.model.AuthenticationResult;
import com.hexagonal.notice.domain.ports.in.auth.AuthenticateUserUseCase;
import com.hexagonal.notice.domain.ports.in.user.RetrieveUserUseCase;
import com.hexagonal.notice.domain.ports.out.GenerateTokenPort;
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
    private final GenerateTokenPort generateTokenPort;

    public AuthService(
            ReactiveAuthenticationManager authenticationManager,
            @Qualifier("retrieveUserBean") RetrieveUserUseCase retrieveUserUseCase,
            GenerateTokenPort generateTokenPort
    ) {
        this.authenticationManager = authenticationManager;
        this.retrieveUserUseCase = retrieveUserUseCase;
        this.generateTokenPort = generateTokenPort;
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
                    .flatMap(user -> {
                        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                                .username(user.getUsername())
                                .password(user.getPassword())
                                .authorities(Collections.singletonList(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER")))
                                .build();

                        return generateTokenPort.generateToken(userDetails)
                                .map(jwt -> {
                                    log.info("User authenticated successfully: {}", command.getUsername());
                                    return AuthenticationResult.builder()
                                            .token(jwt)
                                            .build();
                                });
                    });
        }).onErrorResume(e -> {
            log.error("Authentication failed for user: {}", command.getUsername(), e);
            return Mono.error(new RuntimeException("Invalid credentials"));
        });
    }
}
