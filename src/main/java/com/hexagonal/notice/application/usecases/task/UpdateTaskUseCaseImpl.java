package com.hexagonal.notice.application.usecases.task;

import com.hexagonal.notice.domain.model.Task;
import com.hexagonal.notice.domain.ports.in.task.UpdateTaskUseCase;
import com.hexagonal.notice.domain.ports.out.TaskRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("updateTaskBean")
public class UpdateTaskUseCaseImpl implements UpdateTaskUseCase {
    private final TaskRepositoryPort taskRepository;

    public UpdateTaskUseCaseImpl(TaskRepositoryPort taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Mono<Task> updateTask(Long id, Task task) {
        return taskRepository.update(id, task);
    }
}
