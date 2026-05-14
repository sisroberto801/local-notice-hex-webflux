package com.hexagonal.notice.application.controller;

import com.hexagonal.notice.application.service.AuthService;
import com.hexagonal.notice.infrastructure.dto.AuthenticationRequest;
import com.hexagonal.notice.infrastructure.dto.AuthenticationResponse;
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

    private final AuthService authService;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthenticationResponse>> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        log.info("Authentication request received for user: {}", request.getUsername());

        return authService.authenticate(request)
                .map(response -> {
                    log.info("User authenticated successfully: {}", request.getUsername());
                    return ResponseEntity.ok(response);
                });
    }
}
