package com.hexagonal.notice.infrastructure.repository;

import com.hexagonal.notice.infrastructure.entity.ProfileEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface R2dbcProfileRepository extends R2dbcRepository<ProfileEntity, Long> {
    Mono<ProfileEntity> findByUserId(Long userId);
}