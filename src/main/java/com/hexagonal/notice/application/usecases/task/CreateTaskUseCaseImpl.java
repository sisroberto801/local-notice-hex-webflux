package com.hexagonal.notice.application.usecases.task;

import com.hexagonal.notice.domain.model.Task;
import com.hexagonal.notice.domain.ports.in.task.CreateTaskUseCase;
import com.hexagonal.notice.domain.ports.out.TaskRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("createTaskBean")
public class CreateTaskUseCaseImpl implements CreateTaskUseCase {
    private final TaskRepositoryPort taskRepository;

    public CreateTaskUseCaseImpl(TaskRepositoryPort taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Mono<Task> createTask(Task task) {
        return taskRepository.save(task);
    }
}
