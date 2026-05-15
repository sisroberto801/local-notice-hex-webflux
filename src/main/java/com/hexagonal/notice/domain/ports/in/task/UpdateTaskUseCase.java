package com.hexagonal.notice.domain.ports.in.task;

import com.hexagonal.notice.domain.model.Task;
import reactor.core.publisher.Mono;

public interface UpdateTaskUseCase {
    Mono<Task> updateTask(Long id, Task task);
}
