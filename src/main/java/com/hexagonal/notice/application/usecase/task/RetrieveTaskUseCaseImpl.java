package com.hexagonal.notice.application.usecase.task;

import com.hexagonal.notice.domain.model.Task;
import com.hexagonal.notice.domain.port.in.task.RetrieveTaskUseCase;
import com.hexagonal.notice.domain.port.out.TaskRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component("retrieveTaskBean")
public class RetrieveTaskUseCaseImpl implements RetrieveTaskUseCase {
    private final TaskRepositoryPort taskRepository;

    public RetrieveTaskUseCaseImpl(TaskRepositoryPort taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Mono<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Flux<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Flux<Task> getTasksByStatus(Task.TaskStatus status) {
        return taskRepository.findByStatus(status);
    }
}
