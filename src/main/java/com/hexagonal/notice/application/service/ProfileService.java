package com.hexagonal.notice.application.service;

import com.hexagonal.notice.domain.model.Profile;
import com.hexagonal.notice.domain.port.ProfileRepositoryPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProfileService {
    private final ProfileRepositoryPort profileRepository;

    public ProfileService(ProfileRepositoryPort profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Mono<Profile> createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public Mono<Profile> getProfileById(Long id) {
        return profileRepository.findById(id);
    }

    public Mono<Profile> getProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId);
    }

    public Flux<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Mono<Void> deleteProfile(Long id) {
        return profileRepository.deleteById(id);
    }
}