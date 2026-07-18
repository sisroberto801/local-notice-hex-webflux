package com.hexagonal.notice.application.usecases.user;

import com.hexagonal.notice.domain.ports.in.user.DeleteUserUseCase;
import com.hexagonal.notice.domain.ports.out.UserRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component("deleteUserBean")
@Transactional
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
