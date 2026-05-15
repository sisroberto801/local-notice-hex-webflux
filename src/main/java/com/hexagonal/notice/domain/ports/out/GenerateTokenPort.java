package com.hexagonal.notice.domain.ports.out;

import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface GenerateTokenPort {
    Mono<String> generateToken(UserDetails userDetails);
}
