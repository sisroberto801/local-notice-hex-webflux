package com.hexagonal.notice.application.usecase.user;

import com.hexagonal.notice.domain.model.User;
import com.hexagonal.notice.domain.port.in.user.RetrieveUserUseCase;
import com.hexagonal.notice.domain.port.out.UserRepositoryPort;
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
        return userRepository.findById(id);
    }

    @Override
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }
}
