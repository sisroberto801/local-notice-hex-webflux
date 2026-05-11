package com.hexagonal.notice.domain.port.in.user;

import com.hexagonal.notice.domain.model.User;
import reactor.core.publisher.Mono;

public interface CreateUserUseCase {
    Mono<User> createUser(User user);
}
