package com.hexagonal.notice.application.service;

import com.hexagonal.notice.domain.model.Profile;
import com.hexagonal.notice.domain.port.in.profile.CreateProfileUseCase;
import com.hexagonal.notice.domain.port.in.profile.DeleteProfileUseCase;
import com.hexagonal.notice.domain.port.in.profile.RetrieveProfileUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProfileService implements
        CreateProfileUseCase, RetrieveProfileUseCase, DeleteProfileUseCase {

    private final CreateProfileUseCase createProfileUseCase;
    private final RetrieveProfileUseCase retrieveProfileUseCase;
    private final DeleteProfileUseCase deleteProfileUseCase;

    public ProfileService(
            @Qualifier("createProfileBean") CreateProfileUseCase createProfileUseCase,
            @Qualifier("retrieveProfileBean") RetrieveProfileUseCase retrieveProfileUseCase,
            @Qualifier("deleteProfileBean") DeleteProfileUseCase deleteProfileUseCase
    ) {
        this.createProfileUseCase = createProfileUseCase;
        this.retrieveProfileUseCase = retrieveProfileUseCase;
        this.deleteProfileUseCase = deleteProfileUseCase;
    }

    @Override
    public Mono<Profile> createProfile(Profile profile) {
        return createProfileUseCase.createProfile(profile);
    }

    @Override
    public Mono<Profile> getProfileById(Long id) {
        return retrieveProfileUseCase.getProfileById(id);
    }

    @Override
    public Mono<Profile> getProfileByUserId(Long userId) {
        return retrieveProfileUseCase.getProfileByUserId(userId);
    }

    @Override
    public Flux<Profile> getAllProfiles() {
        return retrieveProfileUseCase.getAllProfiles();
    }

    @Override
    public Mono<Void> deleteProfileById(Long id) {
        return deleteProfileUseCase.deleteProfileById(id);
    }
}