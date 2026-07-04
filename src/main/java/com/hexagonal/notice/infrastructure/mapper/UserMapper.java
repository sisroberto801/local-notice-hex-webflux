package com.hexagonal.notice.infrastructure.mapper;

import com.hexagonal.notice.domain.model.User;
import com.hexagonal.notice.domain.model.in.UserPayload;
import com.hexagonal.notice.infrastructure.entities.UserEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(User user);

    User toDomain(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", expression = "java(Boolean.TRUE)")
    User fromPayload(UserPayload request);

    @AfterMapping
    default void applyEntityDefaults(@MappingTarget UserEntity entity) {
        if (entity.getStatus() == null) {
            entity.setStatus(true);
        }
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(LocalDateTime.now());
        }
        entity.setUpdatedAt(LocalDateTime.now());
    }
}