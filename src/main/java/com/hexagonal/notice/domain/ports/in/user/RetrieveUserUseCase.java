package com.hexagonal.notice.domain.ports.in.user;

import com.hexagonal.notice.domain.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RetrieveUserUseCase {
    Mono<User> getUserById(Long id);

    Mono<User> getUserByUsername(String username);

    Flux<User> getAllUsers();
}
