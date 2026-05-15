package com.hexagonal.notice.domain.ports.in.user;

import reactor.core.publisher.Mono;

public interface DeleteUserUseCase {
    Mono<Void> deleteUserById(Long id);
}
