package com.hexagonal.notice.infrastructure.mapper;

import com.hexagonal.notice.domain.model.Task;
import com.hexagonal.notice.infrastructure.entities.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public Task toDomain(TaskEntity entity) {
        return Task.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(Task.TaskStatus.valueOf(entity.getStatus()))
                .priority(Task.TaskPriority.valueOf(entity.getPriority()))
                .dueDate(entity.getDueDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public TaskEntity toEntity(Task domain) {
        return TaskEntity.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .description(domain.getDescription())
                .status(domain.getStatus().name())
                .priority(domain.getPriority().name())
                .dueDate(domain.getDueDate())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}