package com.hexagonal.notice.domain.ports.out;

import com.hexagonal.notice.domain.model.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskRepositoryPort {
    Mono<Task> save(Task task);

    Mono<Task> findById(Long id);

    Flux<Task> findAll();

    Mono<Task> update(Long id, Task task);

    Mono<Void> deleteById(Long id);

    Flux<Task> findByStatus(Task.TaskStatus status);
}