package com.hexagonal.notice.application.usecase.user;

import com.hexagonal.notice.domain.model.User;
import com.hexagonal.notice.domain.port.in.user.UpdateUserUseCase;
import com.hexagonal.notice.domain.port.out.UserRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("updateUserBean")
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepositoryPort userRepository;

    public UpdateUserUseCaseImpl(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> updateUser(Long id, User user) {
        return userRepository.update(id, user);
    }
}
