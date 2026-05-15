package com.hexagonal.notice.domain.ports.in.task;

import reactor.core.publisher.Mono;

public interface DeleteTaskUseCase {
    Mono<Void> deleteTaskById(Long id);
}
