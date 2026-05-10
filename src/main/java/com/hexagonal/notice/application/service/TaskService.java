package com.hexagonal.notice.application.service;

import com.hexagonal.notice.domain.model.Task;
import com.hexagonal.notice.domain.port.TaskRepositoryPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TaskService {
    private final TaskRepositoryPort taskRepository;

    public TaskService(TaskRepositoryPort taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Mono<Task> createTask(Task task) {
        return taskRepository.save(task);
    }

    public Mono<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Flux<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Mono<Task> updateTask(Long id, Task task) {
        return taskRepository.update(id, task);
    }

    public Mono<Void> deleteTask(Long id) {
        return taskRepository.deleteById(id);
    }

    public Flux<Task> getTasksByStatus(Task.TaskStatus status) {
        return taskRepository.findByStatus(status);
    }
}