package com.hexagonal.notice.application.usecase.user;

import com.hexagonal.notice.domain.model.User;
import com.hexagonal.notice.domain.port.in.user.CreateUserUseCase;
import com.hexagonal.notice.domain.port.out.UserRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("createUserBean")
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepositoryPort userRepository;

    public CreateUserUseCaseImpl(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }
}
