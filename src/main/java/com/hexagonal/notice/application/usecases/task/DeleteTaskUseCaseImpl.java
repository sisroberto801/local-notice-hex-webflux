package com.hexagonal.notice.application.usecases.task;

import com.hexagonal.notice.domain.ports.in.task.DeleteTaskUseCase;
import com.hexagonal.notice.domain.ports.out.TaskRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component("deleteTaskBean")
@Transactional
public class DeleteTaskUseCaseImpl implements DeleteTaskUseCase {
    private final TaskRepositoryPort taskRepository;

    public DeleteTaskUseCaseImpl(TaskRepositoryPort taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Mono<Void> deleteTaskById(Long id) {
        return taskRepository.deleteById(id);
    }
}
