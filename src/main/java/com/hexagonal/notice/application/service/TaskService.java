package com.hexagonal.notice.application.service;

import com.hexagonal.notice.domain.model.Task;
import com.hexagonal.notice.domain.port.in.task.CreateTaskUseCase;
import com.hexagonal.notice.domain.port.in.task.RetrieveTaskUseCase;
import com.hexagonal.notice.domain.port.in.task.UpdateTaskUseCase;
import com.hexagonal.notice.domain.port.in.task.DeleteTaskUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TaskService implements
        CreateTaskUseCase, RetrieveTaskUseCase, UpdateTaskUseCase, DeleteTaskUseCase {
    
    private final CreateTaskUseCase createTaskUseCase;
    private final RetrieveTaskUseCase retrieveTaskUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;

    public TaskService(
            @Qualifier("createTaskBean") CreateTaskUseCase createTaskUseCase,
            @Qualifier("retrieveTaskBean") RetrieveTaskUseCase retrieveTaskUseCase,
            @Qualifier("updateTaskBean") UpdateTaskUseCase updateTaskUseCase,
            @Qualifier("deleteTaskBean") DeleteTaskUseCase deleteTaskUseCase
    ) {
        this.createTaskUseCase = createTaskUseCase;
        this.retrieveTaskUseCase = retrieveTaskUseCase;
        this.updateTaskUseCase = updateTaskUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
    }

    @Override
    public Mono<Task> createTask(Task task) {
        return createTaskUseCase.createTask(task);
    }

    @Override
    public Mono<Task> getTaskById(Long id) {
        return retrieveTaskUseCase.getTaskById(id);
    }

    @Override
    public Flux<Task> getAllTasks() {
        return retrieveTaskUseCase.getAllTasks();
    }

    @Override
    public Mono<Task> updateTask(Long id, Task task) {
        return updateTaskUseCase.updateTask(id, task);
    }

    @Override
    public Mono<Void> deleteTaskById(Long id) {
        return deleteTaskUseCase.deleteTaskById(id);
    }

    @Override
    public Flux<Task> getTasksByStatus(Task.TaskStatus status) {
        return retrieveTaskUseCase.getTasksByStatus(status);
    }
}