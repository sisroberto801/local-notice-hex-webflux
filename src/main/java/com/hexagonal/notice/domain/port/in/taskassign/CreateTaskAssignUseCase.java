package com.hexagonal.notice.domain.port.in.taskassign;

import com.hexagonal.notice.domain.model.TaskAssign;
import reactor.core.publisher.Mono;

public interface CreateTaskAssignUseCase {
    Mono<TaskAssign> createTaskAssign(TaskAssign taskAssign);
}
