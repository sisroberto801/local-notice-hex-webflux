package com.hexagonal.notice.infrastructure.repository.adapter;

import com.hexagonal.notice.domain.model.Profile;
import com.hexagonal.notice.domain.port.out.ProfileRepositoryPort;
import com.hexagonal.notice.infrastructure.entity.ProfileEntity;
import com.hexagonal.notice.infrastructure.mapper.ProfileMapper;
import com.hexagonal.notice.infrastructure.repository.R2dbcProfileRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ProfileRepositoryAdapter implements ProfileRepositoryPort {

    private final R2dbcProfileRepository r2dbcProfileRepository;
    private final ProfileMapper profileMapper;

    public ProfileRepositoryAdapter(
            R2dbcProfileRepository r2dbcProfileRepository,
            ProfileMapper profileMapper
    ) {
        this.r2dbcProfileRepository = r2dbcProfileRepository;
        this.profileMapper = profileMapper;
    }

    @Override
    public Mono<Profile> save(Profile profile) {
        ProfileEntity entity = profileMapper.toEntity(profile);
        return r2dbcProfileRepository.save(entity)
                .map(profileMapper::toDomain);
    }

    @Override
    public Mono<Profile> findById(Long id) {
        return r2dbcProfileRepository.findById(id)
                .map(profileMapper::toDomain);
    }

    @Override
    public Mono<Profile> findByUserId(Long userId) {
        return r2dbcProfileRepository.findByUserId(userId)
                .map(profileMapper::toDomain);
    }

    @Override
    public Flux<Profile> findAll() {
        return r2dbcProfileRepository.findAll()
                .map(profileMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return r2dbcProfileRepository.deleteById(id);
    }
}