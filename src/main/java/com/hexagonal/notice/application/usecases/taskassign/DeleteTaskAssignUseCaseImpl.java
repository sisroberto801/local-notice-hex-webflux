package com.hexagonal.notice.application.usecases.taskassign;

import com.hexagonal.notice.domain.ports.in.taskassign.DeleteTaskAssignUseCase;
import com.hexagonal.notice.domain.ports.out.TaskAssignRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component("deleteTaskAssignBean")
@Transactional
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
