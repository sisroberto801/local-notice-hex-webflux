package com.hexagonal.notice.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("task_assignments")
public class TaskAssignEntity {
    @Id
    private Long id;
    private Long userId;
    private Long taskId;
    private LocalDateTime assignedAt;
    private String assignedBy;
    private LocalDateTime completedAt;
}