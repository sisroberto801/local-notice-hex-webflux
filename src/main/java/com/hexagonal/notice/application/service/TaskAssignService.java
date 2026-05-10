package com.hexagonal.notice.application.service;

import com.hexagonal.notice.domain.model.TaskAssign;
import com.hexagonal.notice.domain.port.TaskAssignRepositoryPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TaskAssignService {
    private final TaskAssignRepositoryPort taskAssignRepository;

    public TaskAssignService(TaskAssignRepositoryPort taskAssignRepository) {
        this.taskAssignRepository = taskAssignRepository;
    }

    public Mono<TaskAssign> assignTask(TaskAssign taskAssign) {
        return taskAssignRepository.save(taskAssign);
    }

    public Mono<TaskAssign> getAssignmentById(Long id) {
        return taskAssignRepository.findById(id);
    }

    public Flux<TaskAssign> getTasksByUser(Long userId) {
        return taskAssignRepository.findByUserId(userId);
    }

    public Flux<TaskAssign> getUsersByTask(Long taskId) {
        return taskAssignRepository.findByTaskId(taskId);
    }

    public Mono<Void> unassignTask(Long userId, Long taskId) {
        return taskAssignRepository.deleteByUserAndTask(userId, taskId);
    }
}