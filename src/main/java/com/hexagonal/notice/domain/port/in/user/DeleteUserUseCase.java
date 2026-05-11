package com.hexagonal.notice.domain.port.in.user;

import reactor.core.publisher.Mono;

public interface DeleteUserUseCase {
    Mono<Void> deleteUserById(Long id);
}
