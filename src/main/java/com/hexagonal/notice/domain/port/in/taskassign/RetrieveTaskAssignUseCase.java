package com.hexagonal.notice.domain.port.in.taskassign;

import com.hexagonal.notice.domain.model.TaskAssign;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RetrieveTaskAssignUseCase {
    Mono<TaskAssign> getTaskAssignById(Long id);

    Flux<TaskAssign> getTaskAssignByUserId(Long userId);

    Flux<TaskAssign> getTaskAssignByTaskId(Long taskId);
}
