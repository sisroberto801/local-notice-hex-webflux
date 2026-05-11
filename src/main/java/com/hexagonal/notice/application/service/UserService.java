package com.hexagonal.notice.application.service;

import com.hexagonal.notice.domain.model.User;
import com.hexagonal.notice.domain.port.in.user.CreateUserUseCase;
import com.hexagonal.notice.domain.port.in.user.DeleteUserUseCase;
import com.hexagonal.notice.domain.port.in.user.RetrieveUserUseCase;
import com.hexagonal.notice.domain.port.in.user.UpdateUserUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService implements
        CreateUserUseCase, RetrieveUserUseCase, UpdateUserUseCase, DeleteUserUseCase {

    private final CreateUserUseCase createUserUseCase;
    private final RetrieveUserUseCase retrieveUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    public UserService(
            @Qualifier("createUserBean") CreateUserUseCase createUserUseCase,
            @Qualifier("retrieveUserBean") RetrieveUserUseCase retrieveUserUseCase,
            @Qualifier("updateUserBean") UpdateUserUseCase updateUserUseCase,
            @Qualifier("deleteUserBean") DeleteUserUseCase deleteUserUseCase
    ) {
        this.createUserUseCase = createUserUseCase;
        this.retrieveUserUseCase = retrieveUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @Override
    public Mono<User> createUser(User user) {
        return createUserUseCase.createUser(user);
    }

    @Override
    public Mono<User> getUserById(Long id) {
        return retrieveUserUseCase.getUserById(id);
    }

    @Override
    public Flux<User> getAllUsers() {
        return retrieveUserUseCase.getAllUsers();
    }

    @Override
    public Mono<User> updateUser(Long id, User user) {
        return updateUserUseCase.updateUser(id, user);
    }

    @Override
    public Mono<Void> deleteUserById(Long id) {
        return deleteUserUseCase.deleteUserById(id);
    }
}