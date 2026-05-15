package com.hexagonal.notice.infrastructure.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    @NonNull
    private Long id;

    @NonNull
    private String username;

    private Boolean status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
