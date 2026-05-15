package com.hexagonal.notice.domain.ports.out;

import com.hexagonal.notice.domain.model.Profile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProfileRepositoryPort {
    Mono<Profile> save(Profile profile);

    Mono<Profile> findById(Long id);

    Mono<Profile> findByUserId(Long userId);

    Flux<Profile> findAll();

    Mono<Void> deleteById(Long id);
}