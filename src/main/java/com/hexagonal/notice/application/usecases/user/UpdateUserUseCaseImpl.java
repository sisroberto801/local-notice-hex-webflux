package com.hexagonal.notice.application.usecases.user;

import com.hexagonal.notice.domain.model.User;
import com.hexagonal.notice.domain.ports.in.user.UpdateUserUseCase;
import com.hexagonal.notice.domain.ports.out.UserRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component("updateUserBean")
@Transactional
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepositoryPort userRepository;

    public UpdateUserUseCaseImpl(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> updateUser(Long id, User user) {
        return userRepository.update(id, user)
                .switchIfEmpty(Mono.empty());
    }
}
