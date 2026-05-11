package com.hexagonal.notice.domain.port.out;

import com.hexagonal.notice.domain.model.TaskAssign;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskAssignRepositoryPort {
    Mono<TaskAssign> save(TaskAssign taskAssign);

    Mono<TaskAssign> findById(Long id);

    Flux<TaskAssign> findByUserId(Long userId);

    Flux<TaskAssign> findByTaskId(Long taskId);

    Flux<TaskAssign> findAll();

    Mono<Void> deleteById(Long id);

    Mono<Void> deleteByUserAndTask(Long userId, Long taskId);
}