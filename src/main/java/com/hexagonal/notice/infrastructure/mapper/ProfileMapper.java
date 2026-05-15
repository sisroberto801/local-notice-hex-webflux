package com.hexagonal.notice.infrastructure.mapper;

import com.hexagonal.notice.domain.model.Profile;
import com.hexagonal.notice.infrastructure.entities.ProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {
    public Profile toDomain(ProfileEntity entity) {
        return Profile.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .email(entity.getEmail())
                .fullName(entity.getFullName())
                .bio(entity.getBio())
                .avatarUrl(entity.getAvatarUrl())
                .phoneNumber(entity.getPhoneNumber())
                .address(entity.getAddress())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public ProfileEntity toEntity(Profile domain) {
        return ProfileEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .email(domain.getEmail())
                .fullName(domain.getFullName())
                .bio(domain.getBio())
                .avatarUrl(domain.getAvatarUrl())
                .phoneNumber(domain.getPhoneNumber())
                .address(domain.getAddress())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}