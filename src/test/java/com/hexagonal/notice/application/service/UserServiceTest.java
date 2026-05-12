package com.hexagonal.notice.application.service;

import com.hexagonal.notice.domain.model.User;
import com.hexagonal.notice.domain.port.in.user.CreateUserUseCase;
import com.hexagonal.notice.domain.port.in.user.DeleteUserUseCase;
import com.hexagonal.notice.domain.port.in.user.RetrieveUserUseCase;
import com.hexagonal.notice.domain.port.in.user.UpdateUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Unit Tests for UserService
 * <p>
 * These tests verify the business logic of UserService in isolation,
 * mocking all external dependencies to ensure pure unit testing.
 * <p>
 * Test Coverage:
 * - User creation
 * - User retrieval by ID
 * - User retrieval (all users)
 * - User updates
 * - User deletion
 * - Error handling scenarios
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private CreateUserUseCase createUserUseCase;

    @Mock
    private RetrieveUserUseCase retrieveUserUseCase;

    @Mock
    private UpdateUserUseCase updateUserUseCase;

    @Mock
    private DeleteUserUseCase deleteUserUseCase;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private static final Long USER_ID = 1L;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(USER_ID)
                .username("testuser")
                .password("password123")
                .status(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should create user successfully when valid user is provided")
    void createUser_shouldReturnCreatedUser_whenValidUserProvided() {
        User newUser = User.builder()
                .username("newuser")
                .password("password123")
                .status(true)
                .build();

        when(createUserUseCase.createUser(any(User.class))).thenReturn(Mono.just(testUser));
        StepVerifier.create(userService.createUser(newUser))
                .expectNext(testUser)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should handle error when user creation fails")
    void createUser_shouldReturnError_whenCreationFails() {
        User newUser = User.builder()
                .username("newuser")
                .password("password123")
                .status(true)
                .build();

        when(createUserUseCase.createUser(any(User.class)))
                .thenReturn(Mono.error(new RuntimeException("User creation failed")));
        StepVerifier.create(userService.createUser(newUser))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    @DisplayName("Should return user when valid ID is provided")
    void getUserById_shouldReturnUser_whenValidIdProvided() {
        when(retrieveUserUseCase.getUserById(USER_ID)).thenReturn(Mono.just(testUser));
        StepVerifier.create(userService.getUserById(USER_ID))
                .expectNext(testUser)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return empty when user ID does not exist")
    void getUserById_shouldReturnEmpty_whenUserDoesNotExist() {
        when(retrieveUserUseCase.getUserById(USER_ID)).thenReturn(Mono.empty());
        StepVerifier.create(userService.getUserById(USER_ID))
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return all users when requested")
    void getAllUsers_shouldReturnAllUsers() {
        User user2 = User.builder()
                .id(2L)
                .username("user2")
                .password("password456")
                .status(true)
                .build();

        when(retrieveUserUseCase.getAllUsers()).thenReturn(Flux.just(testUser, user2));
        StepVerifier.create(userService.getAllUsers())
                .expectNext(testUser)
                .expectNext(user2)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return empty flux when no users exist")
    void getAllUsers_shouldReturnEmpty_whenNoUsersExist() {
        when(retrieveUserUseCase.getAllUsers()).thenReturn(Flux.empty());
        StepVerifier.create(userService.getAllUsers())
                .verifyComplete();
    }

    @Test
    @DisplayName("Should update user successfully when valid data is provided")
    void updateUser_shouldReturnUpdatedUser_whenValidDataProvided() {
        User updatedUser = User.builder()
                .id(USER_ID)
                .username("updateduser")
                .password("newpassword")
                .status(false)
                .build();

        when(updateUserUseCase.updateUser(eq(USER_ID), any(User.class)))
                .thenReturn(Mono.just(updatedUser));
        StepVerifier.create(userService.updateUser(USER_ID, updatedUser))
                .expectNext(updatedUser)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should handle error when user update fails")
    void updateUser_shouldReturnError_whenUpdateFails() {
        User updatedUser = User.builder()
                .id(USER_ID)
                .username("updateduser")
                .build();

        when(updateUserUseCase.updateUser(eq(USER_ID), any(User.class)))
                .thenReturn(Mono.error(new RuntimeException("User not found")));
        StepVerifier.create(userService.updateUser(USER_ID, updatedUser))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    @DisplayName("Should delete user successfully when valid ID is provided")
    void deleteUserById_shouldComplete_whenValidIdProvided() {
        when(deleteUserUseCase.deleteUserById(USER_ID)).thenReturn(Mono.empty());
        StepVerifier.create(userService.deleteUserById(USER_ID))
                .verifyComplete();
    }

    @Test
    @DisplayName("Should handle error when user deletion fails")
    void deleteUserById_shouldReturnError_whenDeletionFails() {
        when(deleteUserUseCase.deleteUserById(USER_ID))
                .thenReturn(Mono.error(new RuntimeException("User not found")));
        StepVerifier.create(userService.deleteUserById(USER_ID))
                .expectError(RuntimeException.class)
                .verify();
    }
}