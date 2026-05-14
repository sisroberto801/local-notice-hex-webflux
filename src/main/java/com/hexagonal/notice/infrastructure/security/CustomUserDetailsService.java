package com.hexagonal.notice.infrastructure.security;

import com.hexagonal.notice.domain.port.in.user.RetrieveUserUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Slf4j
@Service
public class CustomUserDetailsService implements ReactiveUserDetailsService {

    private final RetrieveUserUseCase retrieveUserUseCase;

    public CustomUserDetailsService(
            @Qualifier("retrieveUserBean") RetrieveUserUseCase retrieveUserUseCase
    ) {
        this.retrieveUserUseCase = retrieveUserUseCase;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        log.info("Loading user by username: {}", username);

        return retrieveUserUseCase.getUserByUsername(username)
                .map(user -> org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .build())
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found: " + username)));
    }
}
