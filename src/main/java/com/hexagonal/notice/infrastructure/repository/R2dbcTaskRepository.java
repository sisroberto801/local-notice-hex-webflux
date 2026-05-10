package com.hexagonal.notice.infrastructure.repository;

import com.hexagonal.notice.infrastructure.entity.TaskEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface R2dbcTaskRepository extends R2dbcRepository<TaskEntity, Long> {
    Flux<TaskEntity> findByStatus(String status);
}