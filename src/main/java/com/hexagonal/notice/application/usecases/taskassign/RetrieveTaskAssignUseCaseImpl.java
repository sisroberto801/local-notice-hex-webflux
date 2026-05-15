package com.hexagonal.notice.application.usecases.taskassign;

import com.hexagonal.notice.domain.model.TaskAssign;
import com.hexagonal.notice.domain.ports.in.taskassign.RetrieveTaskAssignUseCase;
import com.hexagonal.notice.domain.ports.out.TaskAssignRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component("retrieveTaskAssignBean")
public class RetrieveTaskAssignUseCaseImpl implements RetrieveTaskAssignUseCase {
    private final TaskAssignRepositoryPort taskAssignRepository;

    public RetrieveTaskAssignUseCaseImpl(TaskAssignRepositoryPort taskAssignRepository) {
        this.taskAssignRepository = taskAssignRepository;
    }

    @Override
    public Mono<TaskAssign> getTaskAssignById(Long id) {
        return taskAssignRepository.findById(id);
    }

    @Override
    public Flux<TaskAssign> getTaskAssignByUserId(Long userId) {
        return taskAssignRepository.findByUserId(userId);
    }

    @Override
    public Flux<TaskAssign> getTaskAssignByTaskId(Long taskId) {
        return taskAssignRepository.findByTaskId(taskId);
    }
}
