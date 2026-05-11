package com.hexagonal.notice.infrastructure.repository.adapter;

import com.hexagonal.notice.domain.model.Task;
import com.hexagonal.notice.domain.port.out.TaskRepositoryPort;
import com.hexagonal.notice.infrastructure.entity.TaskEntity;
import com.hexagonal.notice.infrastructure.mapper.TaskMapper;
import com.hexagonal.notice.infrastructure.repository.R2dbcTaskRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class TaskRepositoryAdapter implements TaskRepositoryPort {

    private final R2dbcTaskRepository r2dbcTaskRepository;
    private final TaskMapper taskMapper;

    public TaskRepositoryAdapter(
            R2dbcTaskRepository r2dbcTaskRepository,
            TaskMapper taskMapper
    ) {
        this.r2dbcTaskRepository = r2dbcTaskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public Mono<Task> save(Task task) {
        TaskEntity entity = taskMapper.toEntity(task);
        return r2dbcTaskRepository.save(entity)
                .map(taskMapper::toDomain);
    }

    @Override
    public Mono<Task> findById(Long id) {
        return r2dbcTaskRepository.findById(id)
                .map(taskMapper::toDomain);
    }

    @Override
    public Flux<Task> findAll() {
        return r2dbcTaskRepository.findAll()
                .map(taskMapper::toDomain);
    }

    @Override
    public Mono<Task> update(Long id, Task task) {
        return r2dbcTaskRepository.findById(id)
                .flatMap(existingEntity -> {
                    TaskEntity updatedEntity = taskMapper.toEntity(task);
                    updatedEntity.setId(id);
                    return r2dbcTaskRepository.save(updatedEntity);
                })
                .map(taskMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return r2dbcTaskRepository.deleteById(id);
    }

    @Override
    public Flux<Task> findByStatus(Task.TaskStatus status) {
        return r2dbcTaskRepository.findByStatus(status.name())
                .map(taskMapper::toDomain);
    }
}