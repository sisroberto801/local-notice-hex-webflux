package com.hexagonal.notice.application.usecase.profile;

import com.hexagonal.notice.domain.model.Profile;
import com.hexagonal.notice.domain.port.in.profile.CreateProfileUseCase;
import com.hexagonal.notice.domain.port.out.ProfileRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("createProfileBean")
public class CreateProfileUseCaseImpl implements CreateProfileUseCase {
    private final ProfileRepositoryPort profileRepository;

    public CreateProfileUseCaseImpl(ProfileRepositoryPort profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Mono<Profile> createProfile(Profile profile) {
        return profileRepository.save(profile);
    }
}
