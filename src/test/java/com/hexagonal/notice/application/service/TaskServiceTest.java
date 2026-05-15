package com.hexagonal.notice.application.service;

import com.hexagonal.notice.domain.model.Task;
import com.hexagonal.notice.domain.ports.in.task.CreateTaskUseCase;
import com.hexagonal.notice.domain.ports.in.task.DeleteTaskUseCase;
import com.hexagonal.notice.domain.ports.in.task.RetrieveTaskUseCase;
import com.hexagonal.notice.domain.ports.in.task.UpdateTaskUseCase;
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

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private CreateTaskUseCase createTaskUseCase;

    @Mock
    private RetrieveTaskUseCase retrieveTaskUseCase;

    @Mock
    private UpdateTaskUseCase updateTaskUseCase;

    @Mock
    private DeleteTaskUseCase deleteTaskUseCase;

    @InjectMocks
    private TaskService taskService;

    private Task testTask;
    private static final Long TASK_ID = 1L;

    @BeforeEach
    void setUp() {
        testTask = Task.builder()
                .id(TASK_ID)
                .title("Test Task")
                .description("Test task description")
                .status(Task.TaskStatus.PENDING)
                .priority(Task.TaskPriority.MEDIUM)
                .dueDate(LocalDateTime.now().plusDays(1))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should create task successfully when valid task is provided")
    void createTask_shouldReturnCreatedTask_whenValidTaskProvided() {
        Task newTask = Task.builder()
                .title("New Task")
                .description("New task description")
                .status(Task.TaskStatus.PENDING)
                .priority(Task.TaskPriority.HIGH)
                .build();

        when(createTaskUseCase.createTask(any(Task.class))).thenReturn(Mono.just(testTask));
        StepVerifier.create(taskService.createTask(newTask))
                .expectNext(testTask)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should handle error when task creation fails")
    void createTask_shouldReturnError_whenCreationFails() {
        Task newTask = Task.builder()
                .title("New Task")
                .build();

        when(createTaskUseCase.createTask(any(Task.class)))
                .thenReturn(Mono.error(new RuntimeException("Task creation failed")));
        StepVerifier.create(taskService.createTask(newTask))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    @DisplayName("Should return task when valid ID is provided")
    void getTaskById_shouldReturnTask_whenValidIdProvided() {
        when(retrieveTaskUseCase.getTaskById(TASK_ID)).thenReturn(Mono.just(testTask));
        StepVerifier.create(taskService.getTaskById(TASK_ID))
                .expectNext(testTask)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return empty when task ID does not exist")
    void getTaskById_shouldReturnEmpty_whenTaskDoesNotExist() {
        when(retrieveTaskUseCase.getTaskById(TASK_ID)).thenReturn(Mono.empty());
        StepVerifier.create(taskService.getTaskById(TASK_ID))
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return all tasks when requested")
    void getAllTasks_shouldReturnAllTasks() {
        Task task2 = Task.builder()
                .id(2L)
                .title("Task 2")
                .status(Task.TaskStatus.IN_PROGRESS)
                .priority(Task.TaskPriority.LOW)
                .build();

        when(retrieveTaskUseCase.getAllTasks()).thenReturn(Flux.just(testTask, task2));
        StepVerifier.create(taskService.getAllTasks())
                .expectNext(testTask)
                .expectNext(task2)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return empty flux when no tasks exist")
    void getAllTasks_shouldReturnEmpty_whenNoTasksExist() {
        when(retrieveTaskUseCase.getAllTasks()).thenReturn(Flux.empty());
        StepVerifier.create(taskService.getAllTasks())
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return tasks by status when valid status is provided")
    void getTasksByStatus_shouldReturnTasks_whenValidStatusProvided() {
        Task pendingTask1 = Task.builder()
                .id(1L)
                .title("Pending Task 1")
                .status(Task.TaskStatus.PENDING)
                .build();

        Task pendingTask2 = Task.builder()
                .id(2L)
                .title("Pending Task 2")
                .status(Task.TaskStatus.PENDING)
                .build();

        when(retrieveTaskUseCase.getTasksByStatus(Task.TaskStatus.PENDING))
                .thenReturn(Flux.just(pendingTask1, pendingTask2));
        StepVerifier.create(taskService.getTasksByStatus(Task.TaskStatus.PENDING))
                .expectNext(pendingTask1)
                .expectNext(pendingTask2)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return empty flux when no tasks exist with given status")
    void getTasksByStatus_shouldReturnEmpty_whenNoTasksWithStatusExist() {
        when(retrieveTaskUseCase.getTasksByStatus(Task.TaskStatus.COMPLETED))
                .thenReturn(Flux.empty());
        StepVerifier.create(taskService.getTasksByStatus(Task.TaskStatus.COMPLETED))
                .verifyComplete();
    }

    @Test
    @DisplayName("Should update task successfully when valid data is provided")
    void updateTask_shouldReturnUpdatedTask_whenValidDataProvided() {
        Task updatedTask = Task.builder()
                .id(TASK_ID)
                .title("Updated Task")
                .description("Updated description")
                .status(Task.TaskStatus.IN_PROGRESS)
                .priority(Task.TaskPriority.HIGH)
                .build();

        when(updateTaskUseCase.updateTask(eq(TASK_ID), any(Task.class)))
                .thenReturn(Mono.just(updatedTask));
        StepVerifier.create(taskService.updateTask(TASK_ID, updatedTask))
                .expectNext(updatedTask)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should handle error when task update fails")
    void updateTask_shouldReturnError_whenUpdateFails() {
        Task updatedTask = Task.builder()
                .id(TASK_ID)
                .title("Updated Task")
                .build();

        when(updateTaskUseCase.updateTask(eq(TASK_ID), any(Task.class)))
                .thenReturn(Mono.error(new RuntimeException("Task not found")));
        StepVerifier.create(taskService.updateTask(TASK_ID, updatedTask))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    @DisplayName("Should delete task successfully when valid ID is provided")
    void deleteTaskById_shouldComplete_whenValidIdProvided() {
        when(deleteTaskUseCase.deleteTaskById(TASK_ID)).thenReturn(Mono.empty());
        StepVerifier.create(taskService.deleteTaskById(TASK_ID))
                .verifyComplete();
    }

    @Test
    @DisplayName("Should handle error when task deletion fails")
    void deleteTaskById_shouldReturnError_whenDeletionFails() {
        when(deleteTaskUseCase.deleteTaskById(TASK_ID))
                .thenReturn(Mono.error(new RuntimeException("Task not found")));
        StepVerifier.create(taskService.deleteTaskById(TASK_ID))
                .expectError(RuntimeException.class)
                .verify();
    }
}
