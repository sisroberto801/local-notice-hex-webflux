package com.hexagonal.notice.infrastructure.repository.adapter;

import com.hexagonal.notice.domain.model.User;
import com.hexagonal.notice.domain.port.out.UserRepositoryPort;
import com.hexagonal.notice.infrastructure.entity.UserEntity;
import com.hexagonal.notice.infrastructure.mapper.UserMapper;
import com.hexagonal.notice.infrastructure.repository.R2dbcUserRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final R2dbcUserRepository r2dbcUserRepository;
    private final UserMapper userMapper;

    public UserRepositoryAdapter(
            R2dbcUserRepository r2dbcUserRepository,
            UserMapper userMapper
    ) {
        this.r2dbcUserRepository = r2dbcUserRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Mono<User> save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        return r2dbcUserRepository.save(entity)
                .map(userMapper::toDomain);
    }

    @Override
    public Mono<User> findById(Long id) {
        return r2dbcUserRepository.findById(id)
                .map(userMapper::toDomain);
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return r2dbcUserRepository.findByUsername(username)
                .map(userMapper::toDomain);
    }

    @Override
    public Flux<User> findAll() {
        return r2dbcUserRepository.findAll()
                .map(userMapper::toDomain);
    }

    @Override
    public Mono<User> update(Long id, User user) {
        return r2dbcUserRepository.findById(id)
                .flatMap(existingEntity -> {
                    UserEntity updatedEntity = userMapper.toEntity(user);
                    updatedEntity.setId(id);
                    return r2dbcUserRepository.save(updatedEntity);
                })
                .map(userMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return r2dbcUserRepository.deleteById(id);
    }
}