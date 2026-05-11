package com.hexagonal.notice.infrastructure.repository.adapter;

import com.hexagonal.notice.domain.model.TaskAssign;
import com.hexagonal.notice.domain.port.out.TaskAssignRepositoryPort;
import com.hexagonal.notice.infrastructure.entity.TaskAssignEntity;
import com.hexagonal.notice.infrastructure.mapper.TaskAssignMapper;
import com.hexagonal.notice.infrastructure.repository.R2dbcTaskAssignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class TaskAssignRepositoryAdapter implements TaskAssignRepositoryPort {
    private final R2dbcTaskAssignRepository r2dbcTaskAssignRepository;
    private final TaskAssignMapper taskAssignMapper;

    @Override
    public Mono<TaskAssign> save(TaskAssign taskAssign) {
        TaskAssignEntity entity = taskAssignMapper.toEntity(taskAssign);
        return r2dbcTaskAssignRepository.save(entity)
                .map(taskAssignMapper::toDomain);
    }

    @Override
    public Mono<TaskAssign> findById(Long id) {
        return r2dbcTaskAssignRepository.findById(id)
                .map(taskAssignMapper::toDomain);
    }

    @Override
    public Flux<TaskAssign> findByUserId(Long userId) {
        return r2dbcTaskAssignRepository.findByUserId(userId)
                .map(taskAssignMapper::toDomain);
    }

    @Override
    public Flux<TaskAssign> findByTaskId(Long taskId) {
        return r2dbcTaskAssignRepository.findByTaskId(taskId)
                .map(taskAssignMapper::toDomain);
    }

    @Override
    public Flux<TaskAssign> findAll() {
        return r2dbcTaskAssignRepository.findAll()
                .map(taskAssignMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return r2dbcTaskAssignRepository.deleteById(id);
    }

    @Override
    public Mono<Void> deleteByUserAndTask(Long userId, Long taskId) {
        return r2dbcTaskAssignRepository.deleteByUserIdAndTaskId(userId, taskId);
    }
}