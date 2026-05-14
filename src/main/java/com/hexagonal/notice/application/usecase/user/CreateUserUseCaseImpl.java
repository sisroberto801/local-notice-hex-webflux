package com.hexagonal.notice.application.usecase.user;

import com.hexagonal.notice.domain.model.User;
import com.hexagonal.notice.domain.port.in.user.CreateUserUseCase;
import com.hexagonal.notice.domain.port.out.UserRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("createUserBean")
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCaseImpl(
            UserRepositoryPort userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<User> createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }
}
