package com.hexagonal.notice.domain.port.in.taskassign;

import reactor.core.publisher.Mono;

public interface DeleteTaskAssignUseCase {
    Mono<Void> deleteTaskAssignByUserIdAndTaskId(Long userId, Long taskId);
}
