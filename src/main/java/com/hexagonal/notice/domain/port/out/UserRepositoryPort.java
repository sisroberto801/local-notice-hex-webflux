package com.hexagonal.notice.domain.port.out;

import com.hexagonal.notice.domain.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepositoryPort {
    Mono<User> save(User user);

    Mono<User> findById(Long id);

    Mono<User> findByUsername(String username);

    Flux<User> findAll();

    Mono<User> update(Long id, User user);

    Mono<Void> deleteById(Long id);
}