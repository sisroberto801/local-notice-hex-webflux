package com.hexagonal.notice.domain.port.in.user;

import com.hexagonal.notice.domain.model.User;
import reactor.core.publisher.Mono;

public interface UpdateUserUseCase {
    Mono<User> updateUser(Long id, User user);
}
