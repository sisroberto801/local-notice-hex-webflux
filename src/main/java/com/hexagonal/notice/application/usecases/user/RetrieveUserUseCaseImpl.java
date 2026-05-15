package com.hexagonal.notice.application.usecases.user;

import com.hexagonal.notice.domain.model.User;
import com.hexagonal.notice.domain.ports.in.user.RetrieveUserUseCase;
import com.hexagonal.notice.domain.ports.out.UserRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component("retrieveUserBean")
public class RetrieveUserUseCaseImpl implements RetrieveUserUseCase {
    private final UserRepositoryPort userRepository;

    public RetrieveUserUseCaseImpl(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> getUserById(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }
}
