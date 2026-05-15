package com.hexagonal.notice.infrastructure.mapper;

import com.hexagonal.notice.domain.model.TaskAssign;
import com.hexagonal.notice.infrastructure.entities.TaskAssignEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskAssignMapper {
    public TaskAssign toDomain(TaskAssignEntity entity) {
        return TaskAssign.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .taskId(entity.getTaskId())
                .assignedAt(entity.getAssignedAt())
                .assignedBy(entity.getAssignedBy())
                .completedAt(entity.getCompletedAt())
                .build();
    }

    public TaskAssignEntity toEntity(TaskAssign domain) {
        return TaskAssignEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .taskId(domain.getTaskId())
                .assignedAt(domain.getAssignedAt())
                .assignedBy(domain.getAssignedBy())
                .completedAt(domain.getCompletedAt())
                .build();
    }
}