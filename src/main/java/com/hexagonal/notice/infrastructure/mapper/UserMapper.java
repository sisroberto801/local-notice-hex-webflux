package com.hexagonal.notice.infrastructure.mapper;

import com.hexagonal.notice.domain.model.User;
import com.hexagonal.notice.domain.model.UserRequest;
import com.hexagonal.notice.domain.model.UserResponse;
import com.hexagonal.notice.infrastructure.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public UserEntity toEntity(User user) {
        if (user == null) return null;

        return UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .status(user.getStatus() != null ? user.getStatus() : true)
                .createdAt(user.getCreatedAt() != null ? user.getCreatedAt() : LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public User toDomain(UserEntity entity) {
        if (entity == null) return null;

        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public User toUserFromRequest(UserRequest request) {
        return (null == request) ?
                null :
                User.builder()
                        .username(request.getUsername())
                        .password(request.getPassword())
                        .status(request.getStatus())
                        .build();
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}