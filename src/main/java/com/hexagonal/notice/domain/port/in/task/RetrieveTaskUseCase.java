package com.hexagonal.notice.domain.port.in.task;

import com.hexagonal.notice.domain.model.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RetrieveTaskUseCase {
    Mono<Task> getTaskById(Long id);

    Flux<Task> getAllTasks();

    Flux<Task> getTasksByStatus(Task.TaskStatus status);
}
