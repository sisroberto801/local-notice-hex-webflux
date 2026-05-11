package com.hexagonal.notice.application.usecase.taskassign;

import com.hexagonal.notice.domain.model.TaskAssign;
import com.hexagonal.notice.domain.port.in.taskassign.CreateTaskAssignUseCase;
import com.hexagonal.notice.domain.port.out.TaskAssignRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("createTaskAssignBean")
public class CreateTaskAssignUseCaseImpl implements CreateTaskAssignUseCase {
    private final TaskAssignRepositoryPort taskAssignRepository;

    public CreateTaskAssignUseCaseImpl(TaskAssignRepositoryPort taskAssignRepository) {
        this.taskAssignRepository = taskAssignRepository;
    }

    @Override
    public Mono<TaskAssign> createTaskAssign(TaskAssign taskAssign) {
        return taskAssignRepository.save(taskAssign);
    }
}
