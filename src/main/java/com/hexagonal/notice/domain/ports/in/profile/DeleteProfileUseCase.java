package com.hexagonal.notice.domain.ports.in.profile;

import reactor.core.publisher.Mono;

public interface DeleteProfileUseCase {
    Mono<Void> deleteProfileById(Long id);
}
