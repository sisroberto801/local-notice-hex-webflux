package com.hexagonal.notice.application.service;

import com.hexagonal.notice.domain.model.TaskAssign;
import com.hexagonal.notice.domain.ports.in.taskassign.CreateTaskAssignUseCase;
import com.hexagonal.notice.domain.ports.in.taskassign.DeleteTaskAssignUseCase;
import com.hexagonal.notice.domain.ports.in.taskassign.RetrieveTaskAssignUseCase;
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
import static org.mockito.Mockito.when;

/**
 * Unit Tests for TaskAssignService
 * <p>
 * These tests verify the business logic of TaskAssignService in isolation,
 * mocking all external dependencies to ensure pure unit testing.
 * <p>
 * Test Coverage:
 * - Task assignment creation
 * - Task assignment retrieval by ID
 * - Task assignment retrieval by User ID
 * - Task assignment retrieval by Task ID
 * - Task assignment deletion
 * - Error handling scenarios
 */
@ExtendWith(MockitoExtension.class)
class TaskAssignServiceTest {

    @Mock
    private CreateTaskAssignUseCase createTaskAssignUseCase;

    @Mock
    private RetrieveTaskAssignUseCase retrieveTaskAssignUseCase;

    @Mock
    private DeleteTaskAssignUseCase deleteTaskAssignUseCase;

    @InjectMocks
    private TaskAssignService taskAssignService;

    private TaskAssign testTaskAssign;
    private static final Long TASK_ASSIGN_ID = 1L;
    private static final Long USER_ID = 1L;
    private static final Long TASK_ID = 1L;

    @BeforeEach
    void setUp() {
        testTaskAssign = TaskAssign.builder()
                .id(TASK_ASSIGN_ID)
                .userId(USER_ID)
                .taskId(TASK_ID)
                .assignedAt(LocalDateTime.now())
                .assignedBy("admin")
                .completedAt(null)
                .build();
    }

    @Test
    @DisplayName("Should create task assignment successfully when valid assignment is provided")
    void createTaskAssign_shouldReturnCreatedTaskAssign_whenValidTaskAssignProvided() {
        TaskAssign newTaskAssign = TaskAssign.builder()
                .userId(USER_ID)
                .taskId(TASK_ID)
                .assignedAt(LocalDateTime.now())
                .assignedBy("manager")
                .build();

        when(createTaskAssignUseCase.createTaskAssign(any(TaskAssign.class)))
                .thenReturn(Mono.just(testTaskAssign));
        StepVerifier.create(taskAssignService.createTaskAssign(newTaskAssign))
                .expectNext(testTaskAssign)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should handle error when task assignment creation fails")
    void createTaskAssign_shouldReturnError_whenCreationFails() {
        TaskAssign newTaskAssign = TaskAssign.builder()
                .userId(USER_ID)
                .taskId(TASK_ID)
                .build();

        when(createTaskAssignUseCase.createTaskAssign(any(TaskAssign.class)))
                .thenReturn(Mono.error(new RuntimeException("Task assignment creation failed")));
        StepVerifier.create(taskAssignService.createTaskAssign(newTaskAssign))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    @DisplayName("Should return task assignment when valid ID is provided")
    void getTaskAssignById_shouldReturnTaskAssign_whenValidIdProvided() {
        when(retrieveTaskAssignUseCase.getTaskAssignById(TASK_ASSIGN_ID))
                .thenReturn(Mono.just(testTaskAssign));
        StepVerifier.create(taskAssignService.getTaskAssignById(TASK_ASSIGN_ID))
                .expectNext(testTaskAssign)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return empty when task assignment ID does not exist")
    void getTaskAssignById_shouldReturnEmpty_whenTaskAssignDoesNotExist() {
        when(retrieveTaskAssignUseCase.getTaskAssignById(TASK_ASSIGN_ID))
                .thenReturn(Mono.empty());
        StepVerifier.create(taskAssignService.getTaskAssignById(TASK_ASSIGN_ID))
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return task assignments when valid User ID is provided")
    void getTaskAssignByUserId_shouldReturnTaskAssigns_whenValidUserIdProvided() {
        TaskAssign taskAssign2 = TaskAssign.builder()
                .id(2L)
                .userId(USER_ID)
                .taskId(2L)
                .assignedAt(LocalDateTime.now())
                .assignedBy("admin")
                .build();

        when(retrieveTaskAssignUseCase.getTaskAssignByUserId(USER_ID))
                .thenReturn(Flux.just(testTaskAssign, taskAssign2));
        StepVerifier.create(taskAssignService.getTaskAssignByUserId(USER_ID))
                .expectNext(testTaskAssign)
                .expectNext(taskAssign2)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return empty flux when user has no task assignments")
    void getTaskAssignByUserId_shouldReturnEmpty_whenUserHasNoTaskAssignments() {
        when(retrieveTaskAssignUseCase.getTaskAssignByUserId(USER_ID))
                .thenReturn(Flux.empty());
        StepVerifier.create(taskAssignService.getTaskAssignByUserId(USER_ID))
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return task assignments when valid Task ID is provided")
    void getTaskAssignByTaskId_shouldReturnTaskAssigns_whenValidTaskIdProvided() {
        TaskAssign taskAssign2 = TaskAssign.builder()
                .id(2L)
                .userId(2L)
                .taskId(TASK_ID)
                .assignedAt(LocalDateTime.now())
                .assignedBy("admin")
                .build();

        when(retrieveTaskAssignUseCase.getTaskAssignByTaskId(TASK_ID))
                .thenReturn(Flux.just(testTaskAssign, taskAssign2));
        StepVerifier.create(taskAssignService.getTaskAssignByTaskId(TASK_ID))
                .expectNext(testTaskAssign)
                .expectNext(taskAssign2)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return empty flux when task has no assignments")
    void getTaskAssignByTaskId_shouldReturnEmpty_whenTaskHasNoAssignments() {
        when(retrieveTaskAssignUseCase.getTaskAssignByTaskId(TASK_ID))
                .thenReturn(Flux.empty());
        StepVerifier.create(taskAssignService.getTaskAssignByTaskId(TASK_ID))
                .verifyComplete();
    }

    @Test
    @DisplayName("Should delete task assignment successfully when valid User ID and Task ID are provided")
    void deleteTaskAssignByUserIdAndTaskId_shouldComplete_whenValidIdsProvided() {
        when(deleteTaskAssignUseCase.deleteTaskAssignByUserIdAndTaskId(USER_ID, TASK_ID))
                .thenReturn(Mono.empty());
        StepVerifier.create(taskAssignService.deleteTaskAssignByUserIdAndTaskId(USER_ID, TASK_ID))
                .verifyComplete();
    }

    @Test
    @DisplayName("Should handle error when task assignment deletion fails")
    void deleteTaskAssignByUserIdAndTaskId_shouldReturnError_whenDeletionFails() {
        when(deleteTaskAssignUseCase.deleteTaskAssignByUserIdAndTaskId(USER_ID, TASK_ID))
                .thenReturn(Mono.error(new RuntimeException("Task assignment not found")));
        StepVerifier.create(taskAssignService.deleteTaskAssignByUserIdAndTaskId(USER_ID, TASK_ID))
                .expectError(RuntimeException.class)
                .verify();
    }
}
