package com.hexagonal.notice.application.service;

import com.hexagonal.notice.domain.model.TaskAssign;
import com.hexagonal.notice.domain.port.in.taskassign.CreateTaskAssignUseCase;
import com.hexagonal.notice.domain.port.in.taskassign.DeleteTaskAssignUseCase;
import com.hexagonal.notice.domain.port.in.taskassign.RetrieveTaskAssignUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TaskAssignService implements
        CreateTaskAssignUseCase, RetrieveTaskAssignUseCase, DeleteTaskAssignUseCase {

    private final CreateTaskAssignUseCase createTaskAssignUseCase;
    private final RetrieveTaskAssignUseCase retrieveTaskAssignUseCase;
    private final DeleteTaskAssignUseCase deleteTaskAssignUseCase;

    public TaskAssignService(
            @Qualifier("createTaskAssignBean") CreateTaskAssignUseCase createTaskAssignUseCase,
            @Qualifier("retrieveTaskAssignBean") RetrieveTaskAssignUseCase retrieveTaskAssignUseCase,
            @Qualifier("deleteTaskAssignBean") DeleteTaskAssignUseCase deleteTaskAssignUseCase
    ) {
        this.createTaskAssignUseCase = createTaskAssignUseCase;
        this.retrieveTaskAssignUseCase = retrieveTaskAssignUseCase;
        this.deleteTaskAssignUseCase = deleteTaskAssignUseCase;
    }

    @Override
    public Mono<TaskAssign> createTaskAssign(TaskAssign taskAssign) {
        return createTaskAssignUseCase.createTaskAssign(taskAssign);
    }

    @Override
    public Mono<TaskAssign> getTaskAssignById(Long id) {
        return retrieveTaskAssignUseCase.getTaskAssignById(id);
    }

    @Override
    public Flux<TaskAssign> getTaskAssignByUserId(Long userId) {
        return retrieveTaskAssignUseCase.getTaskAssignByUserId(userId);
    }

    @Override
    public Flux<TaskAssign> getTaskAssignByTaskId(Long taskId) {
        return retrieveTaskAssignUseCase.getTaskAssignByTaskId(taskId);
    }

    @Override
    public Mono<Void> deleteTaskAssignByUserIdAndTaskId(Long userId, Long taskId) {
        return deleteTaskAssignUseCase.deleteTaskAssignByUserIdAndTaskId(userId, taskId);
    }
}