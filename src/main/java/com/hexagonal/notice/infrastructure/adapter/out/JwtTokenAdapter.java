package com.hexagonal.notice.infrastructure.adapter.out;

import com.hexagonal.notice.domain.ports.out.GenerateTokenPort;
import com.hexagonal.notice.infrastructure.config.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class JwtTokenAdapter implements GenerateTokenPort {

    private final JwtUtil jwtUtil;

    public JwtTokenAdapter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<String> generateToken(UserDetails userDetails) {
        return Mono.fromCallable(() -> {
            try {
                String token = jwtUtil.generateToken(userDetails);
                log.debug("JWT token generated successfully for user: {}", userDetails.getUsername());
                return token;
            } catch (Exception e) {
                log.error("Error generating JWT token for user: {}", userDetails.getUsername(), e);
                throw new RuntimeException("Error generating token", e);
            }
        });
    }
}
