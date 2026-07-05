package com.hexagonal.notice.infrastructure.controller;

import com.hexagonal.notice.domain.model.AuthenticationCommand;
import com.hexagonal.notice.domain.model.AuthenticationResult;
import com.hexagonal.notice.domain.model.in.AuthenticationPayload;
import com.hexagonal.notice.domain.ports.in.auth.AuthenticateUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticateUserUseCase authenticateUserUseCase;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthenticationResult>> authenticate(
            @Valid @RequestBody AuthenticationPayload request
    ) {
        log.info("Authentication request received for user: {}", request.getUsername());

        AuthenticationCommand command = AuthenticationCommand.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();

        return authenticateUserUseCase.authenticate(command)
                .map(result -> {
                    log.info("User authenticated successfully: {}", request.getUsername());
                    return ResponseEntity.ok(result);
                });
    }
}
