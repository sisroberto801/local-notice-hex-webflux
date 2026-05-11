package com.hexagonal.notice.application.usecase.taskassign;

import com.hexagonal.notice.domain.port.in.taskassign.DeleteTaskAssignUseCase;
import com.hexagonal.notice.domain.port.out.TaskAssignRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("deleteTaskAssignBean")
public class DeleteTaskAssignUseCaseImpl implements DeleteTaskAssignUseCase {
    private final TaskAssignRepositoryPort taskAssignRepository;

    public DeleteTaskAssignUseCaseImpl(TaskAssignRepositoryPort taskAssignRepository) {
        this.taskAssignRepository = taskAssignRepository;
    }

    @Override
    public Mono<Void> deleteTaskAssignByUserIdAndTaskId(Long userId, Long taskId) {
        return taskAssignRepository.deleteByUserAndTask(userId, taskId);
    }
}
