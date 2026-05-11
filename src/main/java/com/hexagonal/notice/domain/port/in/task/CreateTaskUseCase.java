package com.hexagonal.notice.domain.port.in.task;

import com.hexagonal.notice.domain.model.Task;
import reactor.core.publisher.Mono;

public interface CreateTaskUseCase {
    Mono<Task> createTask(Task task);
}
