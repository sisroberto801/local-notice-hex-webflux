package com.hexagonal.notice.application.usecase.user;

import com.hexagonal.notice.domain.port.in.user.DeleteUserUseCase;
import com.hexagonal.notice.domain.port.out.UserRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("deleteUserBean")
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private final UserRepositoryPort userRepository;

    public DeleteUserUseCaseImpl(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Void> deleteUserById(Long id) {
        return userRepository.deleteById(id);
    }
}
