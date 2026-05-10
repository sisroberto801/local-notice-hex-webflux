package com.hexagonal.notice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskAssign {
    private Long id;
    private Long userId;
    private Long taskId;
    private LocalDateTime assignedAt;
    private String assignedBy;
    private LocalDateTime completedAt;
}