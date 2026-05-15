package com.hexagonal.notice.infrastructure.repository;

import com.hexagonal.notice.infrastructure.entities.TaskAssignEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface R2dbcTaskAssignRepository extends R2dbcRepository<TaskAssignEntity, Long> {
    Flux<TaskAssignEntity> findByUserId(Long userId);

    Flux<TaskAssignEntity> findByTaskId(Long taskId);

    Mono<Void> deleteByUserIdAndTaskId(Long userId, Long taskId);
}