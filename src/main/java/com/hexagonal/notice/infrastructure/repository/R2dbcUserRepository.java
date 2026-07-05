package com.hexagonal.notice.infrastructure.repository;

import com.hexagonal.notice.infrastructure.entities.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface R2dbcUserRepository extends R2dbcRepository<UserEntity, Long> {
    Mono<UserEntity> findByUsername(String username);

    @Query("SELECT * FROM users WHERE status = :status")
    Flux<UserEntity> findAllByStatus(Boolean status);
}