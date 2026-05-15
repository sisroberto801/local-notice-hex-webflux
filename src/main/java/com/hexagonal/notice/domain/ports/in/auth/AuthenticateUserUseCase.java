package com.hexagonal.notice.domain.ports.in.auth;

import com.hexagonal.notice.domain.model.AuthenticationCommand;
import com.hexagonal.notice.domain.model.AuthenticationResult;
import reactor.core.publisher.Mono;

public interface AuthenticateUserUseCase {
    Mono<AuthenticationResult> authenticate(AuthenticationCommand command);
}
